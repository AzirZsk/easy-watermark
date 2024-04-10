package org.azir.easywatermark.core;

import org.azir.easywatermark.enums.WatermarkTypeEnum;

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
    byte[] execute(WatermarkTypeEnum watermarkType);

    /**
     * Load file data.
     *
     * @param data file data
     */
    void loadFile(byte[] data);
}
