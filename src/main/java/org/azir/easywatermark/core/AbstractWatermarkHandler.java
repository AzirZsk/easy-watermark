package org.azir.easywatermark.core;

import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.core.calculate.AbstractCalculate;
import org.azir.easywatermark.core.calculate.impl.DefaultCalculator;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.font.FontProvider;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.core.graphics.GraphicsProvider;
import org.azir.easywatermark.exception.EasyWatermarkException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @param <F> Font
 * @param <G> Graphics
 * @author zhangshukun
 * @date 2022/11/8
 */
@Slf4j
public abstract class AbstractWatermarkHandler<F, G> implements WatermarkHandler, FontProvider, GraphicsProvider {

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
        log.info("{} init success", this.getClass().getSimpleName());
    }

    protected String watermarkText;

    protected File watermarkFile;

    protected F font;

    protected G graphics;

    protected FontConfig fontConfig;

    protected WatermarkConfig watermarkConfig;

    protected CustomDraw customDraw;

    protected abstract void initGraphics();

    protected abstract void initFont();

    protected abstract void customDraw();

    public void setCustomDraw(CustomDraw customDraw) {
        this.customDraw = customDraw;
    }

    public void watermark(String watermarkText) {
        this.watermarkText = watermarkText;
    }

    public void watermark(File watermarkFile) {
        this.watermarkFile = watermarkFile;
    }
}
