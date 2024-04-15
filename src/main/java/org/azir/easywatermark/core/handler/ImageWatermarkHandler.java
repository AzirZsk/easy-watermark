package org.azir.easywatermark.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.core.AbstractWatermarkHandler;
import org.azir.easywatermark.core.CustomDraw;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.entity.Point;
import org.azir.easywatermark.entity.WatermarkBox;
import org.azir.easywatermark.enums.*;
import org.azir.easywatermark.exception.EasyWatermarkException;
import org.azir.easywatermark.exception.ImageWatermarkHandlerException;
import org.azir.easywatermark.exception.LoadFileException;
import org.azir.easywatermark.exception.LoadFontException;
import org.azir.easywatermark.utils.EasyWatermarkUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zhangshukun
 * @date 2023/02/28
 */
@Slf4j
public class ImageWatermarkHandler extends AbstractWatermarkHandler<Font, Graphics2D> {

    private BufferedImage image;

    private final FontMetrics fontMetrics;

    private final double ascent;

    private int fontHeight;

    public ImageWatermarkHandler(byte[] data, FontConfig fontConfig, WatermarkConfig watermarkConfig) {
        super(data, fontConfig, watermarkConfig);
        this.fontMetrics = graphics.getFontMetrics(font);
        this.ascent = fontMetrics.getAscent();
        if (log.isDebugEnabled()) {
            log.debug("Image height:{}, width:{}", getFileHeight(0), getFileWidth(0));
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
    protected int getFileWidth(int page) {
        return image.getWidth();
    }

    @Override
    protected int getFileHeight(int page) {
        return image.getHeight();
    }

    @Override
    public void customDraw(CustomDraw customDraw) {
        if (customDraw == null) {
            log.warn("Custom draw is null.");
            return;
        }
        customDraw.draw(font, graphics, getFileWidth(0), getFileHeight(0), this);
    }

    @Override
    public byte[] execute(EasyWatermarkTypeEnum watermarkType) {
        if (log.isDebugEnabled()) {
            log.debug("Add watermark. Watermark type:{}", watermarkType);
        }
        switch (watermarkType) {
            case CUSTOM:
                customDraw(customDraw);
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

    @Override
    public void drawDiagonalWatermark() {
        double radians = EasyWatermarkUtils.calcRadians(getFileWidth(0), getFileHeight(0));
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
        graphics.rotate(radians, (double) getFileWidth(0) / 2, (double) getFileHeight(0) / 2);
        WatermarkTypeEnum watermarkType = getWatermarkType();
        int x, y;
        switch (watermarkType) {
            case SINGLE_TEXT:
                x = (getFileWidth(0) - getStringWidth(watermarkText)) / 2;
                y = (getFileHeight(0) - getStringHeight()) / 2;
                drawString(x, y, watermarkText);
                break;
            case MULTI_TEXT:
                WatermarkBox watermarkBox = getWatermarkBox(watermarkType);
                y = (int) ((getFileHeight(0) - watermarkBox.getHeight()) / 2);
                for (int i = 0; i < watermarkTextList.size(); i++) {
                    x = (getFileWidth(0) - getStringWidth(watermarkTextList.get(i))) / 2;
                    drawString(x, y + i * getStringHeight(), watermarkTextList.get(i));
                }
                break;
            case IMAGE:
                // todo
                break;
            default:
                throw new ImageWatermarkHandlerException("Unsupported watermark type.");
        }
    }

    @Override
    public void drawOverspreadWatermark() {
        OverspreadTypeEnum overspreadType = watermarkConfig.getOverspreadType();
        if (log.isDebugEnabled()) {
            log.debug("Overspread type:{}", overspreadType);
        }
        WatermarkTypeEnum watermarkType = getWatermarkType();
        WatermarkBox watermarkBox = getWatermarkBox(watermarkType);
        // Calculate the number of watermarks that can be placed on the image.
        int watermarkCount = (int) (overspreadType.getCoverage() * getFileWidth(0) * getFileHeight(0)
                / (watermarkBox.getWidth() * watermarkBox.getHeight()));
        if (watermarkCount % 2 != 0) {
            watermarkCount++;
        }
        if (log.isDebugEnabled()) {
            log.debug("Watermark count:{}", watermarkCount);
        }
        float boxWidth = watermarkBox.getWidth();
        // Calculate the number of columns and rows of watermarks
        int columnsWatermarkCount = (int) (getFileWidth(0) / boxWidth);
        int lineCount = (int) Math.ceil((float) watermarkCount / columnsWatermarkCount);
        // Calculate the width and height of the blank space
        int blankWidth = (getFileWidth(0) - (int) (boxWidth * columnsWatermarkCount)) / (columnsWatermarkCount + 1);
        int blankHeight = (getFileHeight(0) - (int) (watermarkBox.getHeight() * lineCount)) / (lineCount + 1);

        // draw watermark
        int x = blankWidth;
        int y = blankHeight;
        for (int i = 0; i < watermarkCount; i++) {
            switch (watermarkType) {
                case SINGLE_TEXT:
                    drawString(x, y, watermarkText);
                    break;
                case MULTI_TEXT:
                    drawMultiLineString(x, y, watermarkTextList);
                    break;
                case IMAGE:
                    // todo
                    break;
                default:
                    throw new ImageWatermarkHandlerException("Unsupported watermark type.");
            }
            x += boxWidth + blankWidth;
            if (x + boxWidth > getFileWidth(0)) {
                x = blankWidth;
                y += watermarkBox.getHeight() + blankHeight;
            }
        }
    }

    /**
     * Get watermark box.
     *
     * @param watermarkType watermark type
     * @return watermark box
     */
    private WatermarkBox getWatermarkBox(WatermarkTypeEnum watermarkType) {
        WatermarkBox watermarkBox;
        switch (watermarkType) {
            case SINGLE_TEXT:
                watermarkBox = getStringBox(watermarkText);
                break;
            case MULTI_TEXT:
                watermarkBox = getStringBox(watermarkTextList.toArray(new String[0]));
                break;
            case IMAGE:
                throw new ImageWatermarkHandlerException("Unsupported watermark type.");
            default:
                throw new ImageWatermarkHandlerException("Unsupported watermark type.");
        }
        // check watermark box size is greater than image size
        if (watermarkBox.getWidth() > getFileWidth(0) || watermarkBox.getHeight() > getFileHeight(0)) {
            throw new ImageWatermarkHandlerException("Watermark box size is greater than image size.");
        }
        return watermarkBox;
    }

    @Override
    public void drawCenterWatermark() {
        WatermarkTypeEnum watermarkType = getWatermarkType();
        Point point = null;
        switch (watermarkType) {
            case SINGLE_TEXT:
                point = calcCenterWatermarkPoint(watermarkText);
                drawString(point.getX(), point.getY(), watermarkText);
                break;
            case MULTI_TEXT:
                WatermarkBox watermarkBox = getWatermarkBox(watermarkType);
                int watermarkListHeight = (int) watermarkBox.getHeight();
                int startY = (getFileHeight(0) - watermarkListHeight) / 2;
                if (watermarkConfig.getCenterLocationType() == CenterLocationTypeEnum.TOP_CENTER) {
                    startY = 0;
                } else if (watermarkConfig.getCenterLocationType() == CenterLocationTypeEnum.BOTTOM_CENTER) {
                    startY = getFileHeight(0) - watermarkListHeight;
                }
                for (int i = 0; i < watermarkTextList.size(); i++) {
                    String curWatermarkText = watermarkTextList.get(i);
                    point = calcCenterWatermarkPoint(curWatermarkText);
                    drawString(point.getX(), startY + (i * getStringHeight()), curWatermarkText);
                }
                break;
            case IMAGE:
                break;
            default:
                throw new ImageWatermarkHandlerException("Unsupported watermark type.");
        }
    }

    /**
     * Calculate center watermark point.
     *
     * @param watermarkText watermark text
     * @return center watermark point
     */
    private Point calcCenterWatermarkPoint(String watermarkText) {
        return calcCenterWatermarkPoint(getStringWidth(watermarkText), getStringHeight());
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
        if (fontHeight == 0) {
            fontHeight = fontMetrics.getHeight();
        }
        return fontHeight;
    }

    @Override
    public WatermarkBox getStringBox(String... text) {
        if (text.length == 0) {
            throw new NullPointerException("Text is null.");
        }
        if (text.length == 1) {
            return new WatermarkBox(getStringWidth(text[0]), getStringHeight());
        } else {
            int width = 0;
            int height = 0;
            for (String s : text) {
                width = Math.max(width, getStringWidth(s));
                height += getStringHeight();
            }
            return new WatermarkBox(width, height);
        }
    }

    @Override
    public void drawString(float x, float y, String text) {
        if (log.isDebugEnabled()) {
            log.debug("Draw text. x:{},y:{},text:{}", x, y, text);
        }
        graphics.drawString(text, x, (int) (y + ascent));
    }

    @Override
    public void drawMultiLineString(float x, float y, List<String> text) {
        if (log.isDebugEnabled()) {
            log.debug("Draw multi-line text. x:{},y:{},text:{}", x, y, text);
        }
        for (int i = 0; i < text.size(); i++) {
            drawString(x, y + i * getStringHeight(), text.get(i));
        }
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