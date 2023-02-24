package org.azir.easywatermark.core;

import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.core.calculate.AbstractCalculate;
import org.azir.easywatermark.core.calculate.DefaultCalculate;
import org.azir.easywatermark.core.font.FontProvider;
import org.azir.easywatermark.core.font.FontType;
import org.azir.easywatermark.entity.WatermarkConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhangshukun
 * @date 2022/11/8
 */
@Slf4j
public abstract class AbstractWatermarkHandler<F, G> implements WatermarkHandler, FontProvider {

    protected F font;

    protected WatermarkConfig config = new WatermarkConfig();

    protected AbstractCalculate calculate = new DefaultCalculate();

    public abstract AbstractWatermarkHandler<F, G> font(File file);

    public abstract AbstractWatermarkHandler<F, G> font(InputStream fontFile);

    public abstract AbstractWatermarkHandler<F, G> font(byte[] data);

    public AbstractWatermarkHandler<F, G> config(WatermarkConfig config) {
        this.config = config;
        return this;
    }

    public AbstractWatermarkHandler<F, G> calculate(AbstractCalculate calculate) {
        this.calculate = calculate;
        return this;
    }

    public abstract AbstractWatermarkHandler<F, G> watermark(String watermarkText);

    protected abstract void initGraphics(G graphics) throws IOException;
}
