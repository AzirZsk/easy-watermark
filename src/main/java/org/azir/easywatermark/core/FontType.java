package org.azir.easywatermark.core;

import org.azir.easywatermark.entity.WatermarkParam;

/**
 * @author Azir
 * @date 2022/11/13
 */
public interface FontType<F> {

    /**
     * Load fonts
     *
     * @param param watermark param
     * @return dependency font
     */
    F loadFont(WatermarkParam param);


    /**
     * Get {@code text} width in the font.
     *
     * @param text watermark text
     * @return text width
     */
    double getStringWidth(String text);
}
