package org.easywatermark.exception;

/**
 * @author Azir
 * @date 2023/02/19
 */
public class LoadFileException extends EasyWatermarkException {

    public LoadFileException(String message) {
        super(message);
    }

    public LoadFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
