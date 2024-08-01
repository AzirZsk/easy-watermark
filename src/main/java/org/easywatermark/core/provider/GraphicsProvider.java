package org.easywatermark.core.provider;

import java.util.List;

/**
 * The GraphicsProvider interface defines methods for drawing text, images, and for performing rotation operations.
 * This interface is designed to be used by classes that provide graphical drawing capabilities.
 * <p>
 * The methods in this interface take as parameters the coordinates of the upper left corner of the object to be drawn,
 * the object itself (text or image), and the page number where the object is to be drawn. For text, there are methods
 * for drawing a single line of text and for drawing multiple lines of text.
 * <p>
 * The rotation method allows to rotate the coordinate system by a specified angle around a specified point.
 * <p>
 * The origin of the coordinate system is the upper left corner.
 *
 * @author zhangshukun
 * @since 2024/04/11
 */
public interface GraphicsProvider {

    /**
     * Draw text.
     * <p>
     * The upper left corner is the origin.
     *
     * <p>
     * x, y is text upper left corner coordinate.
     *
     * @param x          x coordinate
     * @param y          y coordinate
     * @param text       text
     * @param pageNumber page number
     */
    void drawString(float x, float y, String text, int pageNumber);

    /**
     * Draw multi-line text.
     * <p>
     * The upper left corner is the origin.
     * Default style: text-align:left;
     * <p>
     * x, y is text upper left corner coordinate.
     *
     * @param x          x coordinate
     * @param y          y coordinate
     * @param text       text
     * @param pageNumber page number
     */
    void drawMultiLineString(float x, float y, List<String> text, int pageNumber);

    /**
     * Draw image.
     * <p>
     * The upper left corner is the origin.
     * <p>
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
