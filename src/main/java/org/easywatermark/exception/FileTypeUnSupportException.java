package org.easywatermark.exception;

/**
 * @author Azir
 * @date 2023/02/20
 */
public class FileTypeUnSupportException extends EasyWatermarkException {
    public FileTypeUnSupportException(String message) {
        super(message);
    }

    public FileTypeUnSupportException(String message, Throwable cause) {
        super(message, cause);
    }
}
