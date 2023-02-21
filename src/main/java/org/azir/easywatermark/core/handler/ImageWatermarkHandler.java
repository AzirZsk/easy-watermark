package org.azir.easywatermark.core.handler;

import org.azir.easywatermark.core.AbstractWatermarkHandler;
import org.azir.easywatermark.entity.WatermarkParam;
import org.azir.easywatermark.exception.PdfWatermarkException;
import sun.font.FontDesignMetrics;


import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Image watermark handler.
 *
 * @author Azir
 * @date 2022/11/14
 */
public class ImageWatermarkHandler extends AbstractWatermarkHandler<Font> {

    @Override
    public Font loadFont(WatermarkParam param) {
        return null;
    }

    @Override
    public double getStringWidth(String text) {
        return 0;
    }

    @Override
    public OutputStream execute() {
        return null;
    }

    @Override
    public void load(byte[] data) {

    }

    @Override
    public void close() throws IOException {

    }
}
