package org.azir.easywatermark.core;

import org.azir.easywatermark.exception.LoadFileException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Azir
 * @date 2023/02/19
 */
public class EasyWatermark {

    /**
     * Load file and auto recognition type.
     *
     * @param file add watermark file.
     * @return watermark handler
     * @see #load(byte[])
     */
    public static WatermarkHandler load(File file) throws IOException {
        return load(new FileInputStream(file));
    }

    /**
     * Load file and auto recognition type.
     *
     * @param inputStream input stream should be read byte data.
     * @return watermark handler
     * @see #load(byte[])
     */
    public static WatermarkHandler load(InputStream inputStream) throws IOException {
        try {
            int available = inputStream.available();
            byte[] data = new byte[available];
            int read = inputStream.read(data);
            if (read != -1) {
                throw new LoadFileException("Part of this data is not read.");
            }
            return load(data);
        } finally {
            inputStream.close();
        }
    }

    /**
     * Load file and auto recognition type.
     *
     * @param bytes file byte data.
     * @return The file type corresponding to the watermark processor;
     */
    public static WatermarkHandler load(byte[] bytes) {
        return null;
    }
}
