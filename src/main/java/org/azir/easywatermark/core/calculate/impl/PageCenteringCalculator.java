package org.azir.easywatermark.core.calculate.impl;

import org.azir.easywatermark.core.calculate.AbstractCalculate;
import org.azir.easywatermark.core.font.FontMetrics;
import org.azir.easywatermark.entity.Point;
import org.azir.easywatermark.entity.WatermarkParam;

import java.util.List;

/**
 * Calculation strategy of watermark in the middle of the page.
 *
 * @author zhangshukun
 * @date 2023/02/24
 */
public class PageCenteringCalculator extends AbstractCalculate {

    @Override
    public WatermarkParam calculateLocation(Point topLeftCornerPoint, Point bottomRightCornerPoint,
                                            FontMetrics fontMetrics, String watermarkText) {
        WatermarkParam res = new WatermarkParam();
        double topLeftCornerPointX = topLeftCornerPoint.getX();
        double bottomRightCornerPointX = bottomRightCornerPoint.getX();
        double stringWidth = fontMetrics.getStringWidth(watermarkText);
        res.setX(Math.abs(topLeftCornerPointX + bottomRightCornerPointX - stringWidth) / 2);

        double topLeftCornerPointY = topLeftCornerPoint.getY();
        double bottomRightCornerPointY = bottomRightCornerPoint.getY();
        double stringHeight = fontMetrics.getStringHeight();
        res.setY(Math.abs(topLeftCornerPointY + bottomRightCornerPointY) / 2 - stringHeight / 4);
        return res;
    }

    @Override
    public List<String> calculateText() {
        return null;
    }
}
