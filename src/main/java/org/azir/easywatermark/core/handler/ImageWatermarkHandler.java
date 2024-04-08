package org.azir.easywatermark.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.core.AbstractWatermarkHandler;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author zhangshukun
 * @date 2023/02/28
 */
@Slf4j
public class ImageWatermarkHandler extends AbstractWatermarkHandler<Font, Graphics2D> {

    private BufferedImage image;

    public ImageWatermarkHandler(byte[] data) {
        super(data);
    }

    @Override
    public AbstractWatermarkHandler<Font, Graphics2D> font(File file) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, file);
            this.font = font.deriveFont((float) config.getFontSize());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public AbstractWatermarkHandler<Font, Graphics2D> font(InputStream fontFile) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            this.font = font.deriveFont((float) config.getFontSize());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public AbstractWatermarkHandler<Font, Graphics2D> font(byte[] data) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data)) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            this.font = font.deriveFont((float) config.getFontSize());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public AbstractWatermarkHandler<Font, Graphics2D> watermark(String watermarkText) {
        return null;
    }

    @Override
    protected void initGraphics(Graphics2D graphics) throws IOException {

    }

    @Override
    public byte[] execute() {
        return new byte[0];
    }

    @Override
    public byte[] execute(String exportFileName) {

    }

    @Override
    public void execute(OutputStream outputStream) {

    }

    @Override
    public void execute(File file) {

    }

    @Override
    public void loadFile(byte[] data) {
        try (InputStream inputStream = new ByteArrayInputStream(data)) {
            this.image = ImageIO.read(inputStream);
        } catch (IOException e) {
            log.error("Load image file error.", e);
        }
    }

    @Override
    public void checkParam() {
        if (Objects.isNull(image)) {
            throw new NullPointerException("Image must not null.");
        }
    }

    @Override
    public void close() throws IOException {
        // nothing
    }

    @Override
    public double getStringWidth(String text) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        return metrics.stringWidth(text);
    }

    @Override
    public double getStringHeight() {
        return 0;
    }

    @Override
    public void load(byte[] fontFile) {
        font(fontFile);
    }
}
