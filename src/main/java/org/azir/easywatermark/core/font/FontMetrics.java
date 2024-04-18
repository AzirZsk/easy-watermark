package org.azir.easywatermark.core.font;

import org.azir.easywatermark.entity.WatermarkBox;

/**
 * @author zhangshukun
 * @date 2023/02/24
 */
public interface FontMetrics {

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

    WatermarkBox getStringBox(String... text);
}
