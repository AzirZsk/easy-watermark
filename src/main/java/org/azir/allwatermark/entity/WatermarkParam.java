package org.azir.allwatermark.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.*;

/**
 * watermark params
 *
 * @author zhangshukun
 * @date 2022/11/8
 */
@Getter
@Setter
@ToString
public class WatermarkParam {

    /**
     * watermark text.
     */
    private String text;

    /**
     * 0.0-1.0 range.
     * 0.0----transparent
     * 1.0----color
     */
    private double transparency = 1.0;

    /**
     * 0-360 range.
     */
    private double inclination;

    /**
     * Horizontal coordinate with the upper left corner of the page as the origin.
     */
    private double x;

    /**
     * Vertical coordinate with the upper left corner of the page as the origin
     */
    private double y;

    private int fontSize;

    private Color fontColor = Color.BLACK;

    /**
     * overspread option.
     * if true, {@link #x} and {@link #y} will be failure。
     */
    private boolean overspread;

    /**
     * overspread option.
     * if true, {@link #x} and {@link #y} will be failure。
     */
    private boolean horizontalCenter;

    /**
     * overspread option.
     * if true, {@link #x} and {@link #y} will be failure。
     */
    private boolean verticalCenter;

    public WatermarkParam(String text) {
        this.text = text;
    }
}
