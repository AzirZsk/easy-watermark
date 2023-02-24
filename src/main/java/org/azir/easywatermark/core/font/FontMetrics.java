package org.azir.easywatermark.core.font;

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
    double getStringWidth(String text);
}
