package org.easywatermark.exception;

/**
 * @author zhangshukun
 * @since 2024/07/02
 */
public class DocxWatermarkHandlerException extends  WatermarkHandlerException{

    public DocxWatermarkHandlerException(Throwable cause) {
        super(cause);
    }

    public DocxWatermarkHandlerException(String message) {
        super(message);
    }

    public DocxWatermarkHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
