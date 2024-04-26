package org.azir.easywatermark.exception;

/**
 * @author Azir
 * @date 2022/11/13
 */
public class PdfWatermarkHandlerException extends WatermarkHandlerException {

    public PdfWatermarkHandlerException(Throwable cause) {
        super(cause);
    }

    public PdfWatermarkHandlerException(String message) {
        super(message);
    }

    public PdfWatermarkHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
