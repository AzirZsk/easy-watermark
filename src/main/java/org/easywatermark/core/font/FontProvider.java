package org.easywatermark.core.font;

import org.easywatermark.entity.WatermarkBox;

/**
 * Font provider.
 *
 * @author zhangshukun
 * @date 2023/02/24
 */
public interface FontProvider extends FontMetrics {

    /**
     * Get the watermark box of the text.
     *
     * @param text text
     * @return watermark box
     */
    WatermarkBox getStringBox(String... text);
}
