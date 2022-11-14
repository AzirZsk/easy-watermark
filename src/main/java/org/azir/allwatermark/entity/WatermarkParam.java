package org.azir.allwatermark.entity;

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

    /**
     * font in the resource path.
     * Current only support TrueTypeFont(.ttf) fonts;
     */
    private String fontFilePath;

    private int fontSize;

    private Color fontColor = Color.BLACK;

    /**
     * overspread option.
     * if true, {@link #x} and {@link #y} will be failure。
     */
    private boolean overspread;

    /**
     * if true, {@link #x} and {@link #y} will be failure。
     */
    private boolean horizontalCenter;

    /**
     * if true, {@link #x} and {@link #y} will be failure。
     */
    private boolean verticalCenter;

    public WatermarkParam(String text) {
        this.text = text;
    }
}
