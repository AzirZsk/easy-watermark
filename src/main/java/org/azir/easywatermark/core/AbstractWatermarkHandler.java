package org.azir.easywatermark.core;

import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.core.calculate.AbstractCalculate;
import org.azir.easywatermark.core.calculate.impl.DefaultCalculator;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.font.FontProvider;
import org.azir.easywatermark.core.config.WatermarkConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhangshukun
 * @date 2022/11/8
 */
@Slf4j
public abstract class AbstractWatermarkHandler<F, G> implements WatermarkHandler, FontProvider {

    public AbstractWatermarkHandler(byte[] data, FontConfig fontConfig, WatermarkConfig watermarkConfig) {
        this.fontConfig = fontConfig;
        this.watermarkConfig = watermarkConfig;
        loadFile(data);
        initFont();
        initGraphics();
        log.info("{} init success", this.getClass().getSimpleName());
    }

    protected String watermarkText;

    protected File watermarkFile;

    protected F font;

    protected G graphics;

    protected FontConfig fontConfig;

    protected WatermarkConfig watermarkConfig;

    protected AbstractCalculate calculate = new DefaultCalculator();

    protected abstract void initGraphics();

    protected abstract void initFont();

    public AbstractWatermarkHandler<F, G> calculate(AbstractCalculate calculate) {
        this.calculate = calculate;
        return this;
    }

    public void watermark(String watermarkText) {
        this.watermarkText = watermarkText;
    }

    public void watermark(File watermarkFile) {
        this.watermarkFile = watermarkFile;
    }
}
