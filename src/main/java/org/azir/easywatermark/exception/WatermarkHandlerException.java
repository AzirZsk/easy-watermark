package org.azir.easywatermark.exception;

/**
 * @author zhangshukun
 * @date 2024/4/20
 */
public class WatermarkHandlerException extends EasyWatermarkException {

    public WatermarkHandlerException(Throwable cause) {
        super(cause);
    }

    public WatermarkHandlerException(String message) {
        super(message);
    }

    public WatermarkHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
