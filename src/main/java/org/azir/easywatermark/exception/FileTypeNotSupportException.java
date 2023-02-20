package org.azir.easywatermark.exception;

/**
 * @author Azir
 * @date 2023/02/20
 */
public class FileTypeNotSupportException extends EasyWatermarkException {
    public FileTypeNotSupportException(String message) {
        super(message);
    }

    public FileTypeNotSupportException(String message, Throwable cause) {
        super(message, cause);
    }
}
