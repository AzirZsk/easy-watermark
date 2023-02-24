package org.azir.easywatermark.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.azir.easywatermark.enums.OverspreadTypeEnum;

import java.awt.*;
import java.util.List;


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
     * Watermark begin x location
     */
    private double x;

    /**
     * Watermark begin y location
     */
    private double y;
}
