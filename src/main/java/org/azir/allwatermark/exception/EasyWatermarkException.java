package org.azir.allwatermark.exception;

import java.io.IOException;

/**
 * @author Azir
 * @date 2022/11/12
 */
public class EasyWatermarkException extends RuntimeException {

    public EasyWatermarkException(String message) {
        super(message);
    }

    public EasyWatermarkException(String message, Throwable cause) {
        super(message, cause);
    }
}
