package org.azir.easywatermark.core;

import org.azir.easywatermark.entity.WatermarkParam;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author zhangshukun
 * @date 2022/11/8
 */
public interface WatermarkHandler {

    /**
     * add watermark to input stream, return handler output stream.
     *
     * @param inputStream input stream to be processed
     * @param param       watermark param
     * @return output stream containing the watermark.
     */
    OutputStream addWatermark(InputStream inputStream, WatermarkParam param) throws IOException;
}
