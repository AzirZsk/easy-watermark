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
     * @param x          x coordinate
     * @param y          y coordinate
     * @param text       text
     * @param pageNumber page number
     */
    void drawString(float x, float y, String text, int pageNumber);

    /**
     * The upper left corner is the origin.
     * Draw multi-line text.
     * x, y is text upper left corner coordinate.
     *
     * @param x          x coordinate
     * @param y          y coordinate
     * @param text       text
     * @param pageNumber page number
     */
    void drawMultiLineString(float x, float y, List<String> text, int pageNumber);

    /**
     * The upper left corner is the origin.
     * Draw image.
     * x, y is text upper left corner coordinate.
     *
     * @param x          x coordinate
     * @param y          y coordinate
     * @param data       image data
     * @param pageNumber page number
     */
    void drawImage(float x, float y, byte[] data, int pageNumber);

    /**
     * Rotate the coordinate system.
     *
     * @param angle      The angle of clockwise rotation
     * @param x          rotate x
     * @param y          rotate y
     * @param pageNumber page number
     */
    void rotate(float angle, float x, float y, int pageNumber);
}
