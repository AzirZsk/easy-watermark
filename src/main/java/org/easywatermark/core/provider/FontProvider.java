package org.easywatermark.core.provider;

import org.easywatermark.entity.WatermarkBox;

/**
 * The FontProvider interface provides methods for obtaining the dimensions of text in a specific font.
 * This interface is designed to be used by classes that need to measure text for layout purposes.
 * <p>
 * The methods in this interface allow to get the width and height of a text string, and to get a WatermarkBox
 * object that represents the bounding box of a text string.
 *
 * @author zhangshukun
 * @date 2023/02/24
 */
public interface FontProvider {

    /**
     * Get {@code text} width in the font.
     *
     * @param text watermark text
     * @return text width
     */
    float getStringWidth(String text);

    /**
     * Get {@code text} height in the font.
     *
     * @return text height
     */
    float getStringHeight();

    /**
     * Get the watermark box of the text.
     *
     * @param text text
     * @return watermark box
     */
    WatermarkBox getStringBox(String... text);
}
