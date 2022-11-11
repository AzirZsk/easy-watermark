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
     * 0-1 range.
     */
    private float transparency;

    /**
     * 0-360 range.
     */
    private float inclination;

    private boolean isOverspread;

}
