package org.easywatermark.entity;

import lombok.Getter;

/**
 * @author zhangshukun
 * @date 2023/02/22
 */
@Getter
public class Point {

    private float x;

    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
