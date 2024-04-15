package org.azir.easywatermark.core;

import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.core.font.FontProvider;
import org.azir.easywatermark.core.graphics.GraphicsProvider;
import org.azir.easywatermark.entity.Point;
import org.azir.easywatermark.enums.CenterLocationTypeEnum;
import org.azir.easywatermark.enums.WatermarkTypeEnum;
import org.azir.easywatermark.exception.EasyWatermarkException;

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
        try {
            this.fontConfig = fontConfig;
            this.watermarkConfig = watermarkConfig;
            loadFile(data);
            initFont();
            initGraphics();
        } catch (Exception e) {
            log.warn("{} init error", this.getClass().getSimpleName(), e);
            throw new EasyWatermarkException(this.getClass().getSimpleName() + " init error");
        }
        log.info("{} init success,watermark is {}", this.getClass().getSimpleName(), getWatermarkType());
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
     * get current width
     *
     * @param page page
     * @return width
     */
    protected abstract int getFileWidth(int page);

    /**
     * get current height
     *
     * @param page page
     * @return height
     */
    protected abstract int getFileHeight(int page);

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
     * @return point
     */
    protected Point calcCenterWatermarkPoint(int watermarkWidth, int watermarkHeight) {
        CenterLocationTypeEnum centerLocationType = watermarkConfig.getCenterLocationType();
        int x, y;
        switch (centerLocationType) {
            case VERTICAL_CENTER:
                x = (getFileWidth(0) - watermarkWidth) / 2;
                y = (getFileHeight(0) - watermarkHeight) / 2;
                break;
            case TOP_CENTER:
                x = (getFileWidth(0) - watermarkWidth) / 2;
                y = 0;
                break;
            case BOTTOM_CENTER:
                x = (getFileWidth(0) - watermarkWidth) / 2;
                y = getFileHeight(0) - watermarkHeight;
                break;
            case LEFT_CENTER:
                x = 0;
                y = (getFileHeight(0) - getStringHeight()) / 2;
                break;
            case RIGHT_CENTER:
                x = getFileWidth(0) - watermarkWidth;
                y = (getFileHeight(0) - watermarkHeight) / 2;
                break;
            default:
                throw new EasyWatermarkException("Unsupported center watermark type.");
        }
        return new Point(x, y);
    }
}
