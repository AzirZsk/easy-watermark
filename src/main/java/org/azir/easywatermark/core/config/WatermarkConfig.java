package org.azir.easywatermark.core.config;

import lombok.Data;
import org.azir.easywatermark.enums.OverspreadTypeEnum;

import java.awt.*;


/**
 * @author zhangshukun
 * @date 2023/02/23
 */
@Data
public class WatermarkConfig {

    private float locationX = 21;

    private float locationY = 21;

    private Color color = Color.BLACK;

    private boolean ignoreRotation = true;

    private float alpha = 1;

    private double fontSize = 60;

    private OverspreadTypeEnum overspreadType;

    /**
     * An angle, in degrees
     */
    private double angle = 0;
}