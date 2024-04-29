package org.easywatermark.core.calculate;

import org.easywatermark.core.font.FontMetrics;
import org.easywatermark.entity.Point;
import org.easywatermark.entity.WatermarkParam;

/**
 * @author zhangshukun
 * @date 2023/02/22
 */
public interface LocationCalculate {

    WatermarkParam calculateLocation(Point topLeftCornerPoint, Point bottomRightCornerPoint,
                                     FontMetrics fontMetrics, String watermarkText);
}
