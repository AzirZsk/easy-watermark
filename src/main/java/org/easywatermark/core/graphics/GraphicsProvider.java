package org.easywatermark.core.graphics;

import java.util.List;

/**
 * @author zhangshukun
 * @since 2024/04/11
 */
public interface GraphicsProvider {

    /**
     * The upper left corner is the origin.
     * Draw text.
     * x, y is text upper left corner coordinate.
     *
     * @param x    x coordinate
     * @param y    y coordinate
     * @param text text
     */
    void drawString(float x, float y, String text);

    /**
     * The upper left corner is the origin.
     * Draw multi-line text.
     * x, y is text upper left corner coordinate.
     *
     * @param x    x coordinate
     * @param y    y coordinate
     * @param text text
     */
    void drawMultiLineString(float x, float y, List<String> text);

    /**
     * The upper left corner is the origin.
     * Draw image.
     * x, y is text upper left corner coordinate.
     *
     * @param x    x coordinate
     * @param y    y coordinate
     * @param data image data
     */
    void drawImage(float x, float y, byte[] data);
}
