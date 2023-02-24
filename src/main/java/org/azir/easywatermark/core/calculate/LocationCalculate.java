package org.azir.easywatermark.core.calculate;

import org.azir.easywatermark.core.font.FontMetrics;
import org.azir.easywatermark.entity.Point;
import org.azir.easywatermark.entity.WatermarkParam;

/**
 * @author zhangshukun
 * @date 2023/02/22
 */
public interface LocationCalculate {

    WatermarkParam calculateLocation(Point topLeftCornerPoint, Point bottomRightCornerPoint,
                                     FontMetrics fontMetrics, String watermarkText);
}
