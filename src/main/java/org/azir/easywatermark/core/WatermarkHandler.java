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
     * add watermark, return handler file bytes.
     *
     * @return Byte array after adding watermark
     */
    byte[] execute();

    byte[] execute(String exportFileName);

    /**
     * add watermark, write result into output stream.
     */
    void execute(OutputStream outputStream);

    /**
     * add watermark, write result into file.
     */
    void execute(File file);

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
