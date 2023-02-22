package org.azir.easywatermark.core;

import org.azir.easywatermark.core.handler.PdfWatermarkHandler;
import org.azir.easywatermark.enums.FileTypeEnums;
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
    public static AbstractWatermarkHandler<?> load(File file) throws IOException {
        return load(new FileInputStream(file));
    }

    /**
     * Load file and auto recognition type.
     *
     * @param inputStream input stream should be read byte data.
     * @return watermark handler
     * @see #load(byte[])
     */
    public static AbstractWatermarkHandler<?> load(InputStream inputStream) throws IOException {
        try {
            int available = inputStream.available();
            byte[] data = new byte[available];
            int read = inputStream.read(data);
            if (read != available) {
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
    public static AbstractWatermarkHandler<?> load(byte[] bytes) {
        AbstractWatermarkHandler<?> handler;
        switch (FileTypeEnums.parseFileType(bytes)) {
            case PDF:
                handler = new PdfWatermarkHandler(bytes);
                break;
            case IMAGE:
                handler = new PdfWatermarkHandler(bytes);
                break;
            case OFFICE:
                handler = new PdfWatermarkHandler(bytes);
                break;
            default:
                handler = new PdfWatermarkHandler(bytes);
                break;
        }
        return handler;
    }
}
