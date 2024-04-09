package org.azir.easywatermark.exception;

/**
 * @author zhangshukun
 * @since 2024/04/09
 */
public class ImageWatermarkHandlerException extends EasyWatermarkException {

    public ImageWatermarkHandlerException(Throwable cause) {
        super(cause);
    }

    public ImageWatermarkHandlerException(String message) {
        super(message);
    }

    public ImageWatermarkHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
