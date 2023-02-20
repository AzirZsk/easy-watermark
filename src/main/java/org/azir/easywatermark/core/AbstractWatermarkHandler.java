package org.azir.easywatermark.core;

import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.entity.WatermarkParam;
import org.azir.easywatermark.utils.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author zhangshukun
 * @date 2022/11/8
 */
@Slf4j
public abstract class AbstractWatermarkHandler<F> implements WatermarkHandler, FontType<F> {

    protected final ThreadLocal<F> fontThreadLocal = new ThreadLocal<>();

    /**
     * @param pageHeight
     * @param pageWidth
     * @param watermarkParam
     */
    protected void calculateLocation(double pageHeight, double pageWidth, WatermarkParam watermarkParam) {
        if (watermarkParam.isOverspread()) {
            if (log.isDebugEnabled()) {
                log.debug("Watermark param is overspread, break calculate watermark in the page location.");
            }
            return;
        }
        watermarkParam.setX(calculateHorizontalLocation(pageWidth, watermarkParam));
        watermarkParam.setY(calculateVerticalLocation(pageHeight, watermarkParam));
    }

    private double calculateHorizontalLocation(double pageWidth, WatermarkParam watermarkParam) {
        if (watermarkParam.isHorizontalCenter()) {
            String text = watermarkParam.getText();
            if (StringUtils.isEmpty(text)) {
                List<String> texts = watermarkParam.getTexts();
                Optional<String> max = texts.stream().max(Comparator.comparing(String::length));
                text = max.orElseThrow(() -> new IllegalArgumentException("Multi-line watermark is empty."));
            }
            double textWidth = getStringWidth(text);
            return (pageWidth - textWidth) / 2;
        }
        return 0;
    }

    private double calculateVerticalLocation(double pageHeight, WatermarkParam watermarkParam) {

        return 0;
    }


}
