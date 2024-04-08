package org.azir.easywatermark.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.core.handler.ImageWatermarkHandler;
import org.azir.easywatermark.core.handler.PdfWatermarkHandler;
import org.azir.easywatermark.enums.FileTypeEnums;
import org.azir.easywatermark.exception.FileTypeNotSupportException;
import org.azir.easywatermark.exception.LoadFileException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Azir
 * @date 2023/02/19
 */
@Data
@Slf4j
public class EasyWatermark {

    private FileTypeEnums fileTypeEnums;

    private String text;

    private File imageFile;

    private WatermarkConfig watermarkConfig;

    private File file;

    private EasyWatermark() {
    }

    public static EasyWatermark create() {
        return new EasyWatermark();
    }

    public EasyWatermark file(File file) {
        this.file = file;
        return this;
    }

    public EasyWatermark text(String text) {
        this.text = text;
        return this;
    }

    public EasyWatermark image(File imageFile) {
        this.imageFile = imageFile;
        return this;
    }

    public EasyWatermark config(WatermarkConfig watermarkConfig) {
        this.watermarkConfig = watermarkConfig;
        return this;
    }

    public byte[] executor() {
        if (file == null) {
            throw new LoadFileException("File is null.");
        }
        String fileName = file.getName();
        String fileType = fileName.split("\\.")[0];
        return executor("easy_watermark."  + fileType);
    }

    public byte[] executor(String exportFileName) {
        checkParam();
        try (AbstractWatermarkHandler<?, ?> handler = load(file)) {
            handler.execute(exportFileName);
        } catch (IOException e) {
            log.error("Load file error.", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    private void checkParam() {
        if (this.file == null) {
            throw new LoadFileException("File is null.");
        }
        if (text == null || imageFile == null) {
            throw new NullPointerException("Text or image file is null.");
        }
    }

    /**
     * Load file and auto recognition type.
     *
     * @param file add watermark file.
     * @return watermark handler
     * @see #load(byte[])
     */
    public static AbstractWatermarkHandler<?, ?> load(File file) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            return load(inputStream);
        } catch (Exception e) {
            throw new LoadFileException(e.getMessage(), e);
        }
    }

    /**
     * Load file and auto recognition type.
     *
     * @param inputStream input stream should be read byte data.
     * @return watermark handler
     * @see #load(byte[])
     */
    public static AbstractWatermarkHandler<?, ?> load(InputStream inputStream) throws IOException {
        int available = inputStream.available();
        byte[] data = new byte[available];
        int read = inputStream.read(data);
        if (read != available) {
            throw new LoadFileException("Part of this data is not read.");
        }
        return load(data);
    }

    /**
     * Load file and auto recognition type.
     *
     * @param bytes file byte data.
     * @return The file type corresponding to the watermark processor;
     */
    public static AbstractWatermarkHandler<?, ?> load(byte[] bytes) {
        AbstractWatermarkHandler<?, ?> handler;
        switch (FileTypeEnums.parseFileType(bytes)) {
            case PDF:
                handler = new PdfWatermarkHandler(bytes);
                break;
            case IMAGE:
                handler = new ImageWatermarkHandler(bytes);
                break;
            default:
                log.warn("File type not support.");
                throw new FileTypeNotSupportException("File type not support.");
        }
        return handler;
    }
}
