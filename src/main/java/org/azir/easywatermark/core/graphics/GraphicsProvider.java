package org.azir.easywatermark.core.graphics;

import java.util.List;

/**
 * @author zhangshukun
 * @since 2024/04/11
 */
public interface GraphicsProvider {

    /**
     * Draw text.
     *
     * @param x    x coordinate
     * @param y    y coordinate
     * @param text text
     */
    void drawString(float x, float y, String text);

    /**
     * Draw multi-line text.
     *
     * @param x    x coordinate
     * @param y    y coordinate
     * @param text text
     */
    void drawMultiLineString(float x, float y, List<String> text);

    /**
     * Draw image.
     *
     * @param x    x coordinate
     * @param y    y coordinate
     * @param data image data
     */
    void drawImage(float x, float y, byte[] data);
}
