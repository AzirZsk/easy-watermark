package org.easywatermark.entity;

import lombok.Data;

/**
 * @author zhangshukun
 * @date 2024/4/12
 */
@Data
public class WatermarkBox {

    private float width;

    private float height;

    public WatermarkBox(float width, float height) {
        this.width = width;
        this.height = height;
    }
}
