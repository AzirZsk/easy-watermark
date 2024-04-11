package org.azir.easywatermark.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.core.AbstractWatermarkHandler;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.enums.CenterLocationTypeEnum;
import org.azir.easywatermark.enums.DiagonalDirectionTypeEnum;
import org.azir.easywatermark.enums.OverspreadTypeEnum;
import org.azir.easywatermark.enums.WatermarkTypeEnum;
import org.azir.easywatermark.exception.*;
import org.azir.easywatermark.utils.EasyWatermarkUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhangshukun
 * @date 2023/02/28
 */
@Slf4j
public class ImageWatermarkHandler extends AbstractWatermarkHandler<Font, Graphics2D> {

    private BufferedImage image;

    private final FontMetrics fontMetrics;

    private final double ascent;

    public ImageWatermarkHandler(byte[] data, FontConfig fontConfig, WatermarkConfig watermarkConfig) {
        super(data, fontConfig, watermarkConfig);
        this.fontMetrics = graphics.getFontMetrics(font);
        this.ascent = fontMetrics.getAscent();
        if (log.isDebugEnabled()) {
            log.debug("Image height:{}, width:{}", image.getHeight(), image.getWidth());
        }
    }

    @Override
    protected void initGraphics() {
        this.graphics = image.createGraphics();
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, watermarkConfig.getAlpha());
        graphics.setComposite(alphaComposite);
        graphics.setColor(watermarkConfig.getColor());
        graphics.setFont(font);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    }

    @Override
    protected void initFont() {
        try {
            Font font;
            if (fontConfig.getFontFile() != null) {
                font = Font.createFont(Font.TRUETYPE_FONT, fontConfig.getFontFile());
            } else {
                font = new Font(fontConfig.getFontName(), fontConfig.getFontStyle(), fontConfig.getFontSize());
            }
            this.font = font.deriveFont(fontConfig.getFontStyle(), (float) fontConfig.getFontSize());
        } catch (FontFormatException | IOException e) {
            log.error("Load font error. Font file:{}", fontConfig.getFontFile(), e);
            throw new LoadFontException("Load font error.", e);
        }
    }

    @Override
    protected void customDraw() {
        if (customDraw == null) {
            log.warn("Custom draw is null.");
        }
        customDraw.draw(font, graphics, image.getWidth(), image.getHeight(), this);
    }

    @Override
    public byte[] execute(WatermarkTypeEnum watermarkType) {
        if (log.isDebugEnabled()) {
            log.debug("Add watermark. Watermark type:{}", watermarkType);
        }
        switch (watermarkType) {
            case CUSTOM:
                customDraw();
                break;
            case CENTER:
                drawCenterWatermark();
                break;
            case DIAGONAL:
                drawDiagonalWatermark();
                break;
            case OVERSPREAD:
                drawOverspreadWatermark();
                break;
            default:
                throw new ImageWatermarkHandlerException("Unsupported watermark type.");
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            boolean writeResult = ImageIO.write(image, "jpeg", outputStream);
            if (writeResult) {
                log.info("Add watermark success.");
                return outputStream.toByteArray();
            } else {
                throw new ImageWatermarkHandlerException("Write image error.");
            }
        } catch (IOException e) {
            throw new EasyWatermarkException("Write image error.", e);
        }
    }

    /**
     * Draw diagonal watermark.
     */
    private void drawDiagonalWatermark() {
        double radians = EasyWatermarkUtils.calcRadians(image.getWidth(), image.getHeight());
        DiagonalDirectionTypeEnum diagonalDirectionType = watermarkConfig.getDiagonalDirectionType();
        switch (diagonalDirectionType) {
            case TOP_TO_BOTTOM:
                break;
            case BOTTOM_TO_TOP:
                radians = -radians;
                break;
            default:
                throw new ImageWatermarkHandlerException("Unsupported diagonal watermark type.");
        }
        graphics.rotate(radians, (double) image.getWidth() / 2, (double) image.getHeight() / 2);

        int x = (image.getWidth() - getStringWidth(watermarkText)) / 2;
        int y = (image.getHeight() - getStringHeight()) / 2;
        drawString(x, y, watermarkText);
    }

    /**
     * Draw overspread watermark.
     */
    private void drawOverspreadWatermark() {
        OverspreadTypeEnum overspreadType = watermarkConfig.getOverspreadType();
        int blankWidth, blankHeight;
        switch (overspreadType) {
            case LOW:
                blankWidth = image.getWidth() / 3;
                blankHeight = image.getHeight() / 3;
                break;
            case NORMAL:
                blankWidth = image.getWidth() / 5;
                blankHeight = image.getHeight() / 5;
                break;
            case HIGH:
                blankWidth = image.getWidth() / 10;
                blankHeight = image.getHeight() / 10;
                break;
            default:
                throw new ImageWatermarkHandlerException("Unsupported overspread watermark type.");
        }

        int x = 0;
        int y = 0;
        int width = image.getWidth();
        int height = image.getHeight();
        int stringWidth = getStringWidth(watermarkText);
        int stringHeight = getStringHeight();
        int xCount = width / stringWidth + 1;
        int yCount = height / stringHeight + 1;
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                drawString(x + i * (stringWidth + blankWidth), y + j * (stringHeight + blankHeight), watermarkText);
            }
        }
    }

    /**
     * Draw center watermark.
     */
    private void drawCenterWatermark() {
        CenterLocationTypeEnum centerLocationType = watermarkConfig.getCenterLocationType();
        int x, y;
        switch (centerLocationType) {
            case VERTICAL_CENTER:
                x = (image.getWidth() - fontMetrics.stringWidth(watermarkText)) / 2;
                y = (image.getHeight() - fontMetrics.getHeight()) / 2;
                break;
            case TOP_CENTER:
                x = (image.getWidth() - fontMetrics.stringWidth(watermarkText)) / 2;
                y = 0;
                break;
            case BOTTOM_CENTER:
                x = (image.getWidth() - fontMetrics.stringWidth(watermarkText)) / 2;
                y = image.getHeight() - fontMetrics.getHeight();
                break;
            case LEFT_CENTER:
                x = 0;
                y = (image.getHeight() - fontMetrics.getHeight()) / 2;
                break;
            case RIGHT_CENTER:
                x = image.getWidth() - fontMetrics.stringWidth(watermarkText);
                y = (image.getHeight() - fontMetrics.getHeight()) / 2;
                break;
            default:
                throw new ImageWatermarkHandlerException("Unsupported center watermark type.");
        }
        drawString(x, y, watermarkText);
    }

    @Override
    public void loadFile(byte[] data) {
        try (InputStream inputStream = new ByteArrayInputStream(data)) {
            this.image = ImageIO.read(inputStream);
        } catch (IOException e) {
            log.error("Load image file error.", e);
            throw new EasyWatermarkException("Load image file error.", e);
        }
    }

    @Override
    public void close() {
        graphics.dispose();
    }

    @Override
    public int getStringWidth(String text) {
        return fontMetrics.stringWidth(text);
    }

    @Override
    public int getStringHeight() {
        return fontMetrics.getHeight();
    }

    @Override
    public void drawString(float x, float y, String text) {
        if (log.isDebugEnabled()) {
            log.debug("Draw text. x:{},y:{},text:{}", x, y, text);
        }
        graphics.drawString(text, x, (int) (y + ascent));
    }

    @Override
    public void drawImage(float x, float y, byte[] data) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(data));
            graphics.drawImage(bufferedImage, (int) x, (int) y, null);
        } catch (IOException e) {
            log.warn("Load image error.", e);
            throw new LoadFileException("Load image error.", e);
        }
    }
}