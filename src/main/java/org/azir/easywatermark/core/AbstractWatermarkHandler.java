package org.azir.easywatermark.core;

import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.core.font.FontProvider;
import org.azir.easywatermark.core.graphics.GraphicsProvider;
import org.azir.easywatermark.entity.Point;
import org.azir.easywatermark.entity.WatermarkBox;
import org.azir.easywatermark.enums.CenterLocationTypeEnum;
import org.azir.easywatermark.enums.EasyWatermarkTypeEnum;
import org.azir.easywatermark.enums.WatermarkTypeEnum;
import org.azir.easywatermark.exception.EasyWatermarkException;
import org.azir.easywatermark.exception.ImageWatermarkHandlerException;
import org.azir.easywatermark.exception.WatermarkHandlerException;

import java.util.List;

/**
 * @param <F> Font
 * @param <G> Graphics
 * @author zhangshukun
 * @date 2022/11/8
 */
@Slf4j
public abstract class AbstractWatermarkHandler<F, G> implements EasyWatermarkHandler, FontProvider, GraphicsProvider {

    protected String watermarkText;

    protected List<String> watermarkTextList;

    protected byte[] watermarkImage;

    protected F font;

    protected G graphics;

    protected FontConfig fontConfig;

    protected WatermarkConfig watermarkConfig;

    protected CustomDraw customDraw;

    public AbstractWatermarkHandler(byte[] data, FontConfig fontConfig, WatermarkConfig watermarkConfig) {
        loadFile(data);
        this.fontConfig = fontConfig;
        this.watermarkConfig = watermarkConfig;
    }

    /**
     * init graphics
     */
    protected abstract void initGraphics();

    /**
     * init font
     */
    protected abstract void initFont();

    /**
     * init environment.
     */
    protected abstract void initEnvironment();

    /**
     * get current width
     *
     * @param page page
     * @return width
     */
    protected abstract float getFileWidth(int page);

    /**
     * get current height
     *
     * @param page page
     * @return height
     */
    protected abstract float getFileHeight(int page);

    /**
     * Get watermark image width.
     *
     * @return watermark image width
     */
    protected abstract float getWatermarkImageWidth();

    /**
     * Get watermark image height.
     *
     * @return watermark image height
     */
    protected abstract float getWatermarkImageHeight();

    protected abstract byte[] execute0(EasyWatermarkTypeEnum watermarkType);

    public void setCustomDraw(CustomDraw customDraw) {
        this.customDraw = customDraw;
    }

    public void watermark(String watermarkText) {
        this.watermarkText = watermarkText;
    }

    public void watermark(byte[] watermarkImage) {
        this.watermarkImage = watermarkImage;
    }

    public void watermark(List<String> watermarkTextList) {
        this.watermarkTextList = watermarkTextList;
    }

    @Override
    public byte[] execute(EasyWatermarkTypeEnum watermarkType) {
        init();
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
        byte[] res = execute0(watermarkType);
        log.info("Add watermark success.");
        return res;
    }

    private void init() {
        try {
            initFont();
            initGraphics();
            initEnvironment();
            log.info("{} init success,watermark is {}", this.getClass().getSimpleName(), getWatermarkType());
        } catch (Exception e) {
            log.error("Init error.", e);
            throw new EasyWatermarkException("Init error.", e);
        }
    }

    protected WatermarkTypeEnum getWatermarkType() {
        if (watermarkText != null) {
            return WatermarkTypeEnum.SINGLE_TEXT;
        } else if (watermarkTextList != null) {
            return WatermarkTypeEnum.MULTI_TEXT;
        } else {
            return WatermarkTypeEnum.IMAGE;
        }
    }

    /**
     * calculate watermark point
     *
     * @param watermarkWidth  watermark width
     * @param watermarkHeight watermark height
     * @param page            page
     * @return point
     */
    protected Point calcCenterWatermarkPoint(float watermarkWidth, float watermarkHeight, int page) {
        CenterLocationTypeEnum centerLocationType = watermarkConfig.getCenterLocationType();
        float x, y;
        switch (centerLocationType) {
            case VERTICAL_CENTER:
                x = (getFileWidth(page) - watermarkWidth) / 2;
                y = (getFileHeight(page) - watermarkHeight) / 2;
                break;
            case TOP_CENTER:
                x = (getFileWidth(page) - watermarkWidth) / 2;
                y = 0;
                break;
            case BOTTOM_CENTER:
                x = (getFileWidth(page) - watermarkWidth) / 2;
                y = getFileHeight(page) - watermarkHeight;
                break;
            case LEFT_CENTER:
                x = 0;
                y = (getFileHeight(page) - getStringHeight()) / 2;
                break;
            case RIGHT_CENTER:
                x = getFileWidth(page) - watermarkWidth;
                y = (getFileHeight(page) - watermarkHeight) / 2;
                break;
            default:
                throw new EasyWatermarkException("Unsupported center watermark type.");
        }
        return new Point(x, y);
    }

    /**
     * Calculate center watermark point.
     *
     * @param watermarkText watermark text
     * @param page          page
     * @return center watermark point
     */
    protected Point calcCenterWatermarkPoint(String watermarkText, int page) {
        return calcCenterWatermarkPoint(getStringWidth(watermarkText), getStringHeight(), page);
    }


    @Override
    public WatermarkBox getStringBox(String... text) {
        if (text.length == 0) {
            throw new NullPointerException("Text is null.");
        }
        if (text.length == 1) {
            return new WatermarkBox(getStringWidth(text[0]), getStringHeight());
        } else {
            float width = 0;
            float height = 0;
            for (String s : text) {
                width = Math.max(width, getStringWidth(s));
                height += getStringHeight();
            }
            return new WatermarkBox(width, height);
        }
    }

    /**
     * Get watermark box.
     *
     * @param watermarkType watermark type
     * @param page          page
     * @param calcAngle     calculate angle
     * @return watermark box
     */
    protected WatermarkBox getWatermarkBox(WatermarkTypeEnum watermarkType, int page, boolean calcAngle) {
        WatermarkBox watermarkBox;
        switch (watermarkType) {
            case SINGLE_TEXT:
                watermarkBox = getStringBox(watermarkText);
                if (calcAngle) {
                    watermarkBox = calcAngleWatermarkBox(watermarkConfig.getAngle(), watermarkBox);
                }
                break;
            case MULTI_TEXT:
                watermarkBox = getStringBox(watermarkTextList.toArray(new String[0]));
                break;
            case IMAGE:
                watermarkBox = new WatermarkBox(getWatermarkImageWidth(), getWatermarkImageHeight());
                break;
            default:
                throw new WatermarkHandlerException("Unsupported watermark type.");
        }
        // check watermark box size is greater than image size
        if (watermarkBox.getWidth() > getFileWidth(page) || watermarkBox.getHeight() > getFileHeight(page)) {
            throw new WatermarkHandlerException("Watermark box size is greater than image size.");
        }
        return watermarkBox;
    }

    private WatermarkBox calcAngleWatermarkBox(float angle, WatermarkBox watermarkBox) {
        if (angle == 0) {
            return watermarkBox;
        }
        double radians = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        float width = (float) (watermarkBox.getWidth() * cos + watermarkBox.getHeight() * sin);
        float height = (float) (watermarkBox.getWidth() * sin + watermarkBox.getHeight() * cos);
        return new WatermarkBox(width, height);
    }
}
