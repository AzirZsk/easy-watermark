package org.azir.easywatermark.exception;

/**
 * @author Azir
 * @date 2022/11/13
 */
public class PdfWatermarkException extends WatermarkHandlerException {

    public PdfWatermarkException(Throwable cause) {
        super(cause);
    }

    public PdfWatermarkException(String message) {
        super(message);
    }

    public PdfWatermarkException(String message, Throwable cause) {
        super(message, cause);
    }
}
