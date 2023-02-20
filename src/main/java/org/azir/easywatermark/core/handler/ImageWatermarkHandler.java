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

    private static final String FIELD_POINT_SIZE = "pointSize";

    private static final String FIELD_SIZE = "size";

    @Override
    public OutputStream addWatermark(InputStream inputStream, WatermarkParam param) throws IOException {
        return null;
    }

    @Override
    public Font getFont(WatermarkParam param) {
        try (InputStream resourceAsStream = getClass().getResourceAsStream(param.getFontFilePath());) {
            Objects.requireNonNull(resourceAsStream, "Font file can not load");
            Font font = Font.createFont(Font.TRUETYPE_FONT, resourceAsStream);
            Field pointSizeField = Font.class.getDeclaredField(FIELD_POINT_SIZE);
            Field sizeField = Font.class.getDeclaredField(FIELD_SIZE);
            pointSizeField.setAccessible(true);
            sizeField.setAccessible(true);
            pointSizeField.set(font, param.getFontSize());
            sizeField.set(font, param.getFontSize());
            return font;
        } catch (Exception e) {
            throw new PdfWatermarkException(e.getMessage(), e);
        }
    }

    @Override
    public double getStringWidth(String text) {
        Font font = fontThreadLocal.get();
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        return metrics.stringWidth(text);
    }
}
