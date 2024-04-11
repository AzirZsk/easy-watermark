package org.azir.easywatermark.core.graphics;

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
     * Draw image.
     *
     * @param x    x coordinate
     * @param y    y coordinate
     * @param data image data
     */
    void drawImage(float x, float y, byte[] data);
}
