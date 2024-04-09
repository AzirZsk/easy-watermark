package org.azir.easywatermark.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.core.AbstractWatermarkHandler;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.enums.WatermarkLocationTypeEnum;
import org.azir.easywatermark.exception.EasyWatermarkException;
import org.azir.easywatermark.exception.ImageWatermarkHandlerException;
import org.azir.easywatermark.exception.LoadFontException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

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
    public byte[] execute(WatermarkLocationTypeEnum watermarkType) {
        if (log.isDebugEnabled()) {
            log.debug("Add watermark. Watermark type:{}", watermarkType);
        }
        switch (watermarkType) {
            case CUSTOM:
                graphics.drawString(watermarkText, watermarkConfig.getLocationX(), watermarkConfig.getLocationY() + (float) ascent);
                break;
            case CENTER:
                break;
            case DIAGONAL:
                log.warn("Diagonal watermark type is not supported yet.");
                break;
            case OVERSPREAD:
                log.warn("Overspread watermark type is not supported yet.");
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
    public double getStringWidth(String text) {
        return fontMetrics.stringWidth(text);
    }

    @Override
    public double getStringHeight() {
        return fontMetrics.getHeight();
    }
}