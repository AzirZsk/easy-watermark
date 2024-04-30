package org.easywatermark.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.easywatermark.core.AbstractWatermarkHandler;
import org.easywatermark.core.CustomDraw;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.entity.Point;
import org.easywatermark.entity.WatermarkBox;
import org.easywatermark.enums.*;
import org.easywatermark.exception.*;
import org.easywatermark.utils.EasyWatermarkUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * @author zhangshukun
 * @date 2023/02/28
 */
@Slf4j
public class ImageWatermarkHandler extends AbstractWatermarkHandler<Font, Graphics2D> {

    private BufferedImage image;

    private FontMetrics fontMetrics;

    private double ascent;

    private int fontHeight;

    private BufferedImage watermarkBufferedImage;

    public ImageWatermarkHandler(byte[] data, FontConfig fontConfig, WatermarkConfig watermarkConfig) {
        super(data, fontConfig, watermarkConfig);
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
    protected void initEnvironment() {
        this.fontMetrics = graphics.getFontMetrics(font);
        this.ascent = fontMetrics.getAscent();
        this.fontHeight = fontMetrics.getHeight();
        if (log.isDebugEnabled()) {
            log.debug("Image height:{}, width:{}", getFileHeight(0), getFileWidth(0));
        }
        if (getWatermarkType() == WatermarkTypeEnum.IMAGE) {
            try {
                this.watermarkBufferedImage = ImageIO.read(new ByteArrayInputStream(super.watermarkImage));
            } catch (IOException e) {
                log.warn("Load image error.", e);
                throw new LoadFileException("Load image error.", e);
            }
        }
    }

    @Override
    protected float getFileWidth(int page) {
        return image.getWidth();
    }

    @Override
    protected float getFileHeight(int page) {
        return image.getHeight();
    }

    @Override
    protected float getWatermarkImageWidth() {
        return watermarkBufferedImage.getWidth();
    }

    @Override
    protected float getWatermarkImageHeight() {
        return watermarkBufferedImage.getHeight();
    }

    @Override
    public void customDraw(CustomDraw customDraw) {
        if (customDraw == null) {
            log.warn("Custom draw is null.");
            return;
        }
    }

    @Override
    public byte[] export(EasyWatermarkTypeEnum watermarkType) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            boolean writeResult = ImageIO.write(image, "jpeg", outputStream);
            if (writeResult) {
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
        float x, y;
        switch (watermarkType) {
            case SINGLE_TEXT:
                x = (getFileWidth(0) - getStringWidth(watermarkText)) / 2;
                y = (getFileHeight(0) - getStringHeight()) / 2;
                drawString(x, y, watermarkText, 0);
                break;
            case MULTI_TEXT:
                WatermarkBox watermarkBox = getWatermarkBox(watermarkType, 0);
                y = (getFileHeight(0) - watermarkBox.getHeight()) / 2;
                for (int i = 0; i < watermarkTextList.size(); i++) {
                    x = (getFileWidth(0) - getStringWidth(watermarkTextList.get(i))) / 2;
                    drawString(x, y + i * getStringHeight(), watermarkTextList.get(i), 0);
                }
                break;
            case IMAGE:
                x = (getFileWidth(0) - getWatermarkImageWidth()) / 2;
                y = (getFileHeight(0) - getWatermarkImageHeight()) / 2;
                drawImage(x, y, super.watermarkImage, 0);
                break;
            default:
                throw new ImageWatermarkHandlerException("Unsupported watermark type.");
        }
    }

    @Override
    public void drawCenterWatermark() {
        WatermarkTypeEnum watermarkType = getWatermarkType();
        Point point;
        switch (watermarkType) {
            case SINGLE_TEXT:
                point = calcCenterWatermarkPoint(watermarkText, 0);
                drawString(point.getX(), point.getY(), watermarkText, 0);
                break;
            case MULTI_TEXT:
                WatermarkBox watermarkBox = getWatermarkBox(watermarkType, 0);
                int watermarkListHeight = (int) watermarkBox.getHeight();
                float startY = (getFileHeight(0) - watermarkListHeight) / 2;
                if (watermarkConfig.getCenterLocationType() == CenterLocationTypeEnum.TOP_CENTER) {
                    startY = 0;
                } else if (watermarkConfig.getCenterLocationType() == CenterLocationTypeEnum.BOTTOM_CENTER) {
                    startY = getFileHeight(0) - watermarkListHeight;
                }
                for (int i = 0; i < watermarkTextList.size(); i++) {
                    String curWatermarkText = watermarkTextList.get(i);
                    point = calcCenterWatermarkPoint(curWatermarkText, 0);
                    drawString(point.getX(), startY + (i * getStringHeight()), curWatermarkText, 0);
                }
                break;
            case IMAGE:
                point = calcCenterWatermarkPoint(watermarkBufferedImage);
                drawImage(point.getX(), point.getY(), super.watermarkImage, 0);
                break;
            default:
                throw new ImageWatermarkHandlerException("Unsupported watermark type.");
        }
    }

    @Override
    public void drawOverspreadWatermark() {
        if (watermarkConfig.getAngle() != 0) {
            if (log.isDebugEnabled()) {
                log.debug("overspread angle:{}", watermarkConfig.getAngle());
            }
            drawOverspreadWatermarkForAngle(watermarkConfig.getAngle());
            return;
        }

        WatermarkBox watermarkBox = getWatermarkBox(getWatermarkType(), 0);
        OverspreadTypeEnum overspreadType = watermarkConfig.getOverspreadType();
        float coverage = overspreadType.getCoverage();
        float watermarkWidth = coverage * getFileWidth(0);
        int columns = (int) (watermarkWidth / watermarkBox.getWidth());
        float blankWidth = getFileWidth(0) - columns * watermarkBox.getWidth();
        // calculate the distance between watermarks
        float k = getFileWidth(0) * 0.01f;
        float widthWatermarkDistance = calcDistanceBetweenWatermarks(blankWidth, k, columns);
        float widthWatermarkDistanceFromPageBorder = widthWatermarkDistance - k;
        // if the distance between watermarks is less than 0, reduce the distance between watermarks
        if (widthWatermarkDistanceFromPageBorder < 0) {
            widthWatermarkDistance = calcDistanceBetweenWatermarks(blankWidth, k * 0.1f, columns);
            widthWatermarkDistanceFromPageBorder = (float) (widthWatermarkDistance - k * 0.1);
        }

        float watermarkHeight = coverage * getFileHeight(0);
        int rows = (int) (watermarkHeight / watermarkBox.getHeight());
        float blankHeight = getFileHeight(0) - rows * watermarkBox.getHeight();
        // calculate the distance between watermarks
        float heightWatermarkDistance = calcDistanceBetweenWatermarks(blankHeight, k, rows);
        float heightWatermarkDistanceFromPageBorder = heightWatermarkDistance - k;
        // if the distance between watermarks is less than 0, reduce the distance between watermarks
        if (heightWatermarkDistanceFromPageBorder < 0) {
            heightWatermarkDistance = calcDistanceBetweenWatermarks(blankHeight, k * 0.1f, rows);
            heightWatermarkDistanceFromPageBorder = (float) (heightWatermarkDistance - k * 0.1);
        }

        float x = widthWatermarkDistanceFromPageBorder;
        float y = heightWatermarkDistanceFromPageBorder;
        for (int i = 0; i < columns * rows; i++) {
            switch (getWatermarkType()) {
                case SINGLE_TEXT:
                    drawString(x, y, watermarkText, 0);
                    break;
                case MULTI_TEXT:
                    drawMultiLineString(x, y, watermarkTextList, 0);
                    break;
                case IMAGE:
                    drawImage(x, y, super.watermarkImage, 0);
                    break;
                default:
                    throw new ImageWatermarkHandlerException("Unsupported watermark type.");
            }
            x += watermarkBox.getWidth() + widthWatermarkDistance;
            if (x > getFileWidth(0)) {
                x = widthWatermarkDistanceFromPageBorder;
                y += watermarkBox.getHeight() + heightWatermarkDistance;
            }
        }
    }

    private void drawOverspreadWatermarkForAngle(float angle) {
        WatermarkBox watermarkBox = getWatermarkBox(getWatermarkType(), 0);
        OverspreadTypeEnum overspreadType = watermarkConfig.getOverspreadType();
        float coverage = overspreadType.getCoverage();
        float curPageWidth = getFileWidth(0);
        float curPageHeight = getFileHeight(0);
        // calculate the maximum width and height of the page after rotation
        if (curPageWidth < curPageHeight) {
            curPageWidth = curPageHeight;
        } else if (curPageHeight < curPageWidth) {
            curPageHeight = curPageWidth;
        }

        float watermarkWidth = coverage * curPageWidth;
        int columns = (int) (watermarkWidth / watermarkBox.getWidth());
        float blankWidth = curPageWidth - columns * watermarkBox.getWidth();
        // calculate the distance between watermarks
        float k = curPageWidth * 0.01f;
        float widthWatermarkDistance = calcDistanceBetweenWatermarks(blankWidth, k, columns);
        float widthWatermarkDistanceFromPageBorder = widthWatermarkDistance - k;

        float watermarkHeight = coverage * curPageHeight;
        int rows = (int) (watermarkHeight / watermarkBox.getHeight());
        float blankHeight = curPageHeight - rows * watermarkBox.getHeight();
        // calculate the distance between watermarks
        float heightWatermarkDistance = calcDistanceBetweenWatermarks(blankHeight, k, rows);
        float heightWatermarkDistanceFromPageBorder = heightWatermarkDistance - k;

        float newOriginX = getFileWidth(0) / 2;
        float newOriginY = getFileHeight(0) / 2;
        graphics.rotate(Math.toRadians(angle), newOriginX, newOriginY);
        // Why x/y add or reduce half of the width or height?
        // Because after rotating the page, there may be airstrikes in the four corners of the page
        float x = widthWatermarkDistanceFromPageBorder - getFileWidth(0) / 2;
        for (; x < getFileWidth(0) * 3 / 2; x += watermarkBox.getWidth() + widthWatermarkDistance) {
            float y = getFileHeight(0) - heightWatermarkDistanceFromPageBorder - watermarkBox.getHeight() + getFileHeight(0) / 2;
            for (; y > 0 - getFileHeight(0) / 2; y -= watermarkBox.getHeight() + heightWatermarkDistance) {
                switch (getWatermarkType()) {
                    case SINGLE_TEXT:
                        drawString(x, y, watermarkText, 0);
                        break;
                    case MULTI_TEXT:
                        drawMultiLineString(x, y, watermarkTextList, 0);
                        break;
                    case IMAGE:
                        drawImage(x, y, super.watermarkImage, 0);
                        break;
                    default:
                        throw new PdfWatermarkHandlerException("Unsupported watermark type.");
                }
            }
        }
    }

    private Point calcCenterWatermarkPoint(BufferedImage watermarkImage) {
        return calcCenterWatermarkPoint(watermarkImage.getWidth(), watermarkImage.getHeight(), 0);
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
    public float getStringWidth(String text) {
        return fontMetrics.stringWidth(text);
    }

    @Override
    public float getStringHeight() {
        return fontHeight;
    }

    @Override
    public void drawString(float x, float y, String text, int pageNumber) {
        if (log.isDebugEnabled()) {
            log.debug("Draw text. x:{},y:{},text:{},pageNo:{}", x, y, text, pageNumber);
        }
        graphics.drawString(text, x, (int) (y + ascent));
    }

    @Override
    public void drawMultiLineString(float x, float y, List<String> text, int pageNumber) {
        if (log.isDebugEnabled()) {
            log.debug("Draw multi-line text. x:{},y:{},text:{}", x, y, text);
        }
        for (int i = 0; i < text.size(); i++) {
            drawString(x, y + i * getStringHeight(), text.get(i), 0);
        }
    }

    @Override
    public void drawImage(float x, float y, byte[] data, int pageNumber) {
        if (log.isDebugEnabled()) {
            log.debug("Draw image. x:{},y:{}", x, y);
        }
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(data));
            graphics.drawImage(bufferedImage, (int) x, (int) y, null);
        } catch (IOException e) {
            log.warn("Load image error.", e);
            throw new LoadFileException("Load image error.", e);
        }
    }

    @Override
    public void rotate(float angle, float x, float y, int pageNumber) {
        graphics.rotate(Math.toRadians(angle), x, y);
    }
}