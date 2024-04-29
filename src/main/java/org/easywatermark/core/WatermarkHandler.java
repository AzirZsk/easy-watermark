package org.easywatermark.core;

import org.easywatermark.enums.EasyWatermarkTypeEnum;

import java.io.Closeable;

/**
 * @author zhangshukun
 * @date 2022/11/8
 */
public interface WatermarkHandler extends Closeable {

    /**
     * add watermark, return handler file bytes.
     *
     * @return Byte array after adding watermark
     */
    byte[] execute(EasyWatermarkTypeEnum watermarkType);

    /**
     * Load file data.
     *
     * @param data file data
     */
    void loadFile(byte[] data);
}
