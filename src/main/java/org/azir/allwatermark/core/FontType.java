package org.azir.allwatermark.core;

import org.azir.allwatermark.entity.WatermarkParam;

/**
 * @author Azir
 * @date 2022/11/13
 */
public interface FontType<F> {

    /**
     * Access to different dependency on the fonts.
     *
     * @param param watermark param
     * @return dependency font
     */
    F getFont(WatermarkParam param);
}
