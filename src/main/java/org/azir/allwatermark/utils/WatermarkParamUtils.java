package org.azir.allwatermark.utils;

import org.azir.allwatermark.entity.WatermarkParam;
import org.azir.allwatermark.exception.WatermarkParamException;

import java.util.List;
import java.util.Objects;

/**
 * @author Azir
 * @date 2022/11/12
 */
public class WatermarkParamUtils {

    /**
     * Check watermark param illegal.
     *
     * @param param watermark param.
     */
    public static void checkParam(WatermarkParam param) {
        if (param == null) {
            throw new NullPointerException("Watermark param is null.");
        }
        if (param.getTransparency() < 0 || param.getTransparency() > 1) {
            throw new IllegalArgumentException("Transparency should be more than 0 and less than 1.");
        }
        String text = param.getText();
        List<String> texts = param.getTexts();
        if (Objects.nonNull(text) && !text.isEmpty() && Objects.nonNull(texts) && !texts.isEmpty()) {
            throw new WatermarkParamException("");
        }
        boolean isCenter = param.isHorizontalCenter() || param.isOverspread();
        if (param.isOverspread() && isCenter) {
            throw new WatermarkParamException("Overspread and center can not same time open.");
        }
    }

    public static WatermarkParam getDefaultParam(String text) {
        return new WatermarkParam(text);
    }

    /**
     * Get watermark parameters centered by page.
     *
     * @return watermark parameters centered
     */
    public static WatermarkParam getCenterParam(String text) {
        WatermarkParam res = new WatermarkParam(text);
        res.setHorizontalCenter(true);
        res.setVerticalCenter(true);
        return res;
    }

    /**
     * @param param will be change watermark params
     * @see #getCenterParam(String)
     */
    public static void setCenter(WatermarkParam param) {
        param.setHorizontalCenter(true);
        param.setVerticalCenter(true);
    }
}
