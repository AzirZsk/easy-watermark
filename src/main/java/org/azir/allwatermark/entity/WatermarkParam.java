package org.azir.allwatermark.entity;

import lombok.Data;

/**
 * watermark params
 *
 * @author zhangshukun
 * @date 2022/11/8
 */
@Data
public class WatermarkParam {

    /**
     * watermark text.
     */
    private String text;

    /**
     * 0.0-1.0 range.
     */
    private double transparency;

    /**
     * 0-360 range.
     */
    private double inclination;

    /**
     * overspread option.
     * if true, {@link #x} and {@link #y} will be failure。
     */
    private boolean isOverspread;

    /**
     * Horizontal coordinate with the upper left corner of the page as the origin.
     */
    private double x;

    /**
     * Vertical coordinate with the upper left corner of the page as the origin
     */
    private double y;

    private int fontSize;
}
