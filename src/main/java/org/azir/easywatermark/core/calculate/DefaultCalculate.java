package org.azir.easywatermark.core.calculate;

import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.core.font.FontMetrics;
import org.azir.easywatermark.entity.Point;
import org.azir.easywatermark.entity.WatermarkParam;

import java.util.List;

/**
 * @author zhangshukun
 * @date 2023/02/23
 */
@Slf4j
public class DefaultCalculate extends AbstractCalculate {

    @Override
    public List<String> calculateText() {
        log.info("");
        return super.calculateText();
    }

    @Override
    public WatermarkParam calculateLocation(Point topLeftCornerPoint, Point bottomRightCornerPoint, FontMetrics fontMetrics) {
        return new WatermarkParam();
    }
}
