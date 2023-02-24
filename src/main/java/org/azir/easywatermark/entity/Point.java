package org.azir.easywatermark.entity;

import lombok.Getter;

/**
 * @author zhangshukun
 * @date 2023/02/22
 */
@Getter
public class Point {

    private double x;

    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
