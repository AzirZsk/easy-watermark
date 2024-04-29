package org.easywatermark.exception;

/**
 * @author zhangshukun
 * @since 2024/04/09
 */
public class LoadFontException extends LoadFileException {

    public LoadFontException(String message) {
        super(message);
    }

    public LoadFontException(String message, Throwable cause) {
        super(message, cause);
    }
}
