package org.azir.easywatermark.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.core.handler.ImageWatermarkHandler;
import org.azir.easywatermark.enums.FileTypeEnums;
import org.azir.easywatermark.enums.WatermarkTypeEnum;
import org.azir.easywatermark.exception.FileTypeUnSupportException;
import org.azir.easywatermark.exception.LoadFileException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    private File imageFile;

    private WatermarkConfig watermarkConfig = new WatermarkConfig();

    private FontConfig fontConfig = new FontConfig();

    private File file;

    private WatermarkTypeEnum watermarkLocationType = WatermarkTypeEnum.CENTER;

    private CustomDraw customDraw;

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

    public EasyWatermark text(List<String> textList) {
        this.textList = textList;
        return this;
    }

    public EasyWatermark text(String... text) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, text);
        this.textList = list;
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

    public EasyWatermark config(FontConfig fontConfig) {
        this.fontConfig = fontConfig;
        return this;
    }

    public EasyWatermark watermarkType(WatermarkTypeEnum watermarkTypeEnum) {
        this.watermarkLocationType = watermarkTypeEnum;
        return this;
    }

    public EasyWatermark customDraw(CustomDraw customDraw) {
        this.customDraw = customDraw;
        return this;
    }

    public byte[] executor() {
        if (file == null) {
            throw new LoadFileException("File is null.");
        }
        checkParam();
        try (AbstractWatermarkHandler<?, ?> handler = load(file, fontConfig, watermarkConfig)) {
            if (text != null) {
                handler.watermark(text);
            } else if (textList != null) {
                handler.watermark(textList);
            } else {
                handler.watermark(imageFile);
            }
            if (customDraw != null) {
                handler.setCustomDraw(customDraw);
            }
            return handler.execute(watermarkLocationType);
        } catch (IOException e) {
            log.error("Load file error.", e);
            throw new RuntimeException(e);
        }
    }

    private void checkParam() {
        if (this.file == null) {
            throw new LoadFileException("File is null.");
        }
        if (text == null && textList == null && imageFile == null) {
            throw new NullPointerException("Watermark text or image file is null.");
        }
        if ((text != null || textList != null) && imageFile != null) {
            throw new IllegalArgumentException("Watermark text and image file must not null.");
        }
    }

    /**
     * Load file and auto recognition type.
     *
     * @param file add watermark file.
     * @return watermark handler
     */
    public static AbstractWatermarkHandler<?, ?> load(File file, FontConfig fontConfig, WatermarkConfig watermarkConfig) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            return load(inputStream, fontConfig, watermarkConfig);
        } catch (Exception e) {
            throw new LoadFileException(e.getMessage(), e);
        }
    }

    /**
     * Load file and auto recognition type.
     *
     * @param inputStream input stream should be read byte data.
     * @return watermark handler
     */
    public static AbstractWatermarkHandler<?, ?> load(InputStream inputStream, FontConfig fontConfig, WatermarkConfig watermarkConfig) throws IOException {
        int available = inputStream.available();
        byte[] data = new byte[available];
        int read = inputStream.read(data);
        if (read != available) {
            throw new LoadFileException("Part of this data is not read.");
        }
        return load(data, fontConfig, watermarkConfig);
    }

    /**
     * Load file and auto recognition type.
     *
     * @param bytes file byte data.
     * @return The file type corresponding to the watermark processor;
     */
    public static AbstractWatermarkHandler<?, ?> load(byte[] bytes, FontConfig fontConfig, WatermarkConfig watermarkConfig) {
        AbstractWatermarkHandler<?, ?> handler;
        switch (FileTypeEnums.parseFileType(bytes)) {
            case PDF:
            case IMAGE:
                handler = new ImageWatermarkHandler(bytes, fontConfig, watermarkConfig);
                break;
            default:
                log.warn("File type not support.");
                throw new FileTypeUnSupportException("File type not support.");
        }
        return handler;
    }
}
