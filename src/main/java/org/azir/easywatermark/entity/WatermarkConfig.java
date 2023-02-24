package org.azir.easywatermark.entity;

import lombok.Data;

import java.awt.*;

/**
 * @author zhangshukun
 * @date 2023/02/23
 */
@Data
public class WatermarkConfig {

    private Color color = Color.BLACK;

    private boolean ignoreRotation = true;

    private double alpha = 1;

    private double fontSize = 20;

    /**
     * An angle, in degrees
     */
    private double angle = 0;
}
