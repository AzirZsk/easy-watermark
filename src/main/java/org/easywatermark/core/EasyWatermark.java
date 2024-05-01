package org.easywatermark.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.core.handler.ImageWatermarkHandler;
import org.easywatermark.core.handler.PdfWatermarkHandler;
import org.easywatermark.enums.EasyWatermarkTypeEnum;
import org.easywatermark.enums.FileTypeEnums;
import org.easywatermark.exception.EasyWatermarkException;
import org.easywatermark.exception.FileTypeUnSupportException;
import org.easywatermark.exception.LoadFileException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Azir
 * @date 2023/02/19
 */
@Data
@Slf4j
public class EasyWatermark {

    private FileTypeEnums fileTypeEnums;

    private String text;

    private List<String> textList;

    private byte[] imageByte;

    private WatermarkConfig watermarkConfig = new WatermarkConfig();

    private FontConfig fontConfig = new FontConfig();

    private File file;

    private byte[] fileData;

    private EasyWatermarkTypeEnum watermarkType = EasyWatermarkTypeEnum.CENTER;

    private EasyWatermarkCustomDraw easyWatermarkCustomDraw;

    private EasyWatermark() {
    }

    public static EasyWatermark create() {
        return new EasyWatermark();
    }

    public EasyWatermark file(File file) {
        this.file = file;
        return this;
    }

    public EasyWatermark file(byte[] fileData) {
        this.fileData = fileData;
        return this;
    }

    public EasyWatermark text(String text) {
        cleanWatermark();
        this.text = text;
        return this;
    }

    public EasyWatermark text(List<String> textList) {
        cleanWatermark();
        this.textList = textList;
        return this;
    }

    public EasyWatermark text(String... text) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, text);
        cleanWatermark();
        this.textList = list;
        return this;
    }

    public EasyWatermark image(byte[] imageFile) {
        if (!FileTypeEnums.isImage(imageFile)) {
            throw new LoadFileException("Image file is not support.");
        }
        cleanWatermark();
        this.imageByte = imageFile;
        return this;
    }

    public EasyWatermark config(WatermarkConfig watermarkConfig) {
        this.watermarkConfig = watermarkConfig;
        return this;
    }

    public EasyWatermark config(FontConfig fontConfig) {
        this.fontConfig = fontConfig;
        return this;
    }

    public EasyWatermark easyWatermarkType(EasyWatermarkTypeEnum easyWatermarkTypeEnum) {
        this.watermarkType = easyWatermarkTypeEnum;
        return this;
    }

    public EasyWatermark customDraw(EasyWatermarkCustomDraw easyWatermarkCustomDraw) {
        this.easyWatermarkCustomDraw = easyWatermarkCustomDraw;
        return this;
    }

    /**
     * Watermark executor.
     *
     * @return Watermark byte data.
     */
    public byte[] executor() {
        checkParam();
        if (file != null) {
            try (InputStream inputStream = Files.newInputStream(file.toPath())) {
                fileData = new byte[inputStream.available()];
                inputStream.read(fileData);
            } catch (IOException e) {
                log.error("Load file error.", e);
                throw new RuntimeException(e);
            }
        }
        try (AbstractWatermarkHandler<?, ?> handler = load(fileData, fontConfig, watermarkConfig)) {
            if (text != null) {
                handler.watermark(text);
            } else if (textList != null) {
                handler.watermark(textList);
            } else {
                handler.watermark(imageByte);
            }
            if (easyWatermarkCustomDraw != null) {
                handler.setCustomDraw(easyWatermarkCustomDraw);
            }
            return handler.execute(watermarkType);
        } catch (IOException e) {
            log.error("Watermark error.", e);
            throw new EasyWatermarkException(e);
        }
    }

    private void checkParam() {
        if (this.file == null && this.fileData == null) {
            throw new LoadFileException("File and FileData is null.");
        }
        if (easyWatermarkCustomDraw != null) {
            return;
        }
        if (text == null && textList == null && imageByte == null) {
            throw new NullPointerException("Watermark text or image file is null.");
        }
        if ((text != null || textList != null) && imageByte != null) {
            throw new IllegalArgumentException("Watermark text and image file must not null.");
        }
    }

    /**
     * Load file and auto recognition type.
     *
     * @param bytes file byte data.
     * @return The file type corresponding to the watermark processor;
     */
    private static AbstractWatermarkHandler<?, ?> load(byte[] bytes, FontConfig fontConfig, WatermarkConfig watermarkConfig) {
        AbstractWatermarkHandler<?, ?> handler;
        switch (FileTypeEnums.parseFileType(bytes)) {
            case PDF:
                handler = new PdfWatermarkHandler(bytes, fontConfig, watermarkConfig);
                break;
            case IMAGE:
                handler = new ImageWatermarkHandler(bytes, fontConfig, watermarkConfig);
                break;
            default:
                log.warn("File type not support.");
                throw new FileTypeUnSupportException("File type not support.");
        }
        return handler;
    }

    /**
     * Clean watermark.
     */
    private void cleanWatermark() {
        this.text = null;
        this.textList = null;
        this.imageByte = null;
    }
}
