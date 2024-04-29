package org.easywatermark.core.calculate.impl;

import lombok.extern.slf4j.Slf4j;
import org.easywatermark.core.calculate.AbstractCalculate;
import org.easywatermark.core.font.FontMetrics;
import org.easywatermark.entity.Point;
import org.easywatermark.entity.WatermarkParam;

import java.util.Collections;
import java.util.List;

/**
 * Default calculation policy class.
 *
 * @author zhangshukun
 * @date 2023/02/23
 */
@Slf4j
public class DefaultCalculator extends AbstractCalculate {

    @Override
    public List<String> calculateText() {
        log.info("");
        return Collections.emptyList();
    }

    @Override
    public WatermarkParam calculateLocation(Point topLeftCornerPoint, Point bottomRightCornerPoint,
                                            FontMetrics fontMetrics, String watermarkText) {
        return new WatermarkParam();
    }
}
