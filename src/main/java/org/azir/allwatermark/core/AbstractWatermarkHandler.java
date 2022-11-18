package org.azir.allwatermark.core;

import lombok.extern.slf4j.Slf4j;
import org.azir.allwatermark.entity.WatermarkParam;

/**
 * @author zhangshukun
 * @date 2022/11/8
 */
@Slf4j
public abstract class AbstractWatermarkHandler<F> implements WatermarkHandler, FontType<F> {

    protected final ThreadLocal<F> FONT_THREAD_LOCAL = new ThreadLocal<>();

    protected void handler(double pageHeight, double pageWidth, WatermarkParam watermarkParam) {
        if (watermarkParam.isOverspread()) {
            if (log.isDebugEnabled()) {
                log.debug("Watermark param is overspread, break calculate watermark in the page location.");
            }
            return;
        }
        if (watermarkParam.isHorizontalCenter()) {
            watermarkParam.setX(pageWidth / 2);
        }
        if (watermarkParam.isVerticalCenter()) {
            watermarkParam.setY(pageHeight / 2);
        }
    }

}
