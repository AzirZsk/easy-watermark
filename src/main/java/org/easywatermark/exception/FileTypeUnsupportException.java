package org.easywatermark.exception;

/**
 * @author Azir
 * @date 2023/02/20
 */
public class FileTypeUnsupportException extends EasyWatermarkException {
    public FileTypeUnsupportException(String message) {
        super(message);
    }

    public FileTypeUnsupportException(String message, Throwable cause) {
        super(message, cause);
    }
}
