package org.azir.easywatermark.core;

import org.azir.easywatermark.entity.WatermarkParam;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author zhangshukun
 * @date 2022/11/8
 */
public interface WatermarkHandler extends Closeable {

    /**
     * add watermark to input stream, return handler output stream.
     *
     * @return output stream containing the watermark.
     */
    byte[] execute();


    /**
     * Load file data.
     *
     * @param data file data
     */
    void loadFile(byte[] data);

    /**
     * Check class param is present.
     */
    void checkParam();
}
