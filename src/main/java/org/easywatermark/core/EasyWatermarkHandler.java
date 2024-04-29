package org.easywatermark.core;

/**
 * @author zhangshukun
 * @date 2024/4/14
 */
public interface EasyWatermarkHandler extends WatermarkHandler {

    /**
     * Custom draw.
     *
     * @param customDraw custom draw
     */
    void customDraw(CustomDraw customDraw);

    /**
     * Draw center watermark.
     */
    void drawCenterWatermark();

    /**
     * Draw overspread watermark.
     */
    void drawOverspreadWatermark();

    /**
     * Draw diagonal watermark.
     */
    void drawDiagonalWatermark();
}
