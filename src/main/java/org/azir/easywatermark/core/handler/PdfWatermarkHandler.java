package org.azir.easywatermark.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.blend.BlendMode;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import org.azir.easywatermark.core.AbstractWatermarkHandler;
import org.azir.easywatermark.entity.Point;
import org.azir.easywatermark.entity.WatermarkParam;
import org.azir.easywatermark.exception.PdfWatermarkException;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;


/**
 * Use apache pdfbox dependency to pdf add watermark.
 *
 * @author Azir
 * @date 2022/11/12
 */
@Slf4j
public class PdfWatermarkHandler extends AbstractWatermarkHandler<PDFont, PDPageContentStream> {

    private PDDocument document;

    private String watermark;

    public PdfWatermarkHandler(byte[] data) {
        super(data);
    }

    @Override
    public void load(byte[] fontFile) {
        Objects.requireNonNull(document);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fontFile)) {
            font = PDType0Font.load(document, byteArrayInputStream);
        } catch (IOException e) {
            throw new PdfWatermarkException("Load font file error.", e);
        }
    }

    @Override
    public AbstractWatermarkHandler<PDFont, PDPageContentStream> font(InputStream fontFile) {
        Objects.requireNonNull(document);
        try {
            font = PDType0Font.load(document, fontFile);
        } catch (IOException e) {
            throw new PdfWatermarkException("Load font file error.", e);
        }
        return this;
    }

    @Override
    public AbstractWatermarkHandler<PDFont, PDPageContentStream> font(File file) {
        Objects.requireNonNull(document);
        try {
            font = PDType0Font.load(document, file);
        } catch (IOException e) {
            throw new PdfWatermarkException("Load font file error.", e);
        }
        return this;
    }

    @Override
    public AbstractWatermarkHandler<PDFont, PDPageContentStream> font(byte[] data) {
        load(data);
        return this;
    }

    @Override
    public void loadFile(byte[] data) {
        try {
            document = PDDocument.load(data);
        } catch (IOException e) {
            throw new PdfWatermarkException("", e);
        }
    }

    @Override
    public void checkParam() {
        if (Objects.isNull(document)) {
            throw new NullPointerException("Document must not null. May by file has not yet been loaded.");
        }
        if (Objects.isNull(watermark)) {
            throw new NullPointerException("Watermark text must not null.");
        }
        if (Objects.isNull(font)) {
            throw new NullPointerException("Font must not null.");
        }
    }

    @Override
    public double getStringWidth(String text) {
        try {
            return font.getStringWidth(text) / 1000 * config.getFontSize();
        } catch (IOException e) {
            throw new PdfWatermarkException("Calculate font build string width error.", e);
        }
    }

    @Override
    public double getStringHeight() {
        return config.getFontSize();
    }

    @Override
    public byte[] execute() {
        execute0();
        // return result
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            document.save(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new PdfWatermarkException(e);
        }
    }

    @Override
    public void execute(OutputStream outputStream) {
        execute0();
        try {
            document.save(outputStream);
        } catch (IOException e) {
            throw new PdfWatermarkException(e);
        }
    }

    @Override
    public void execute(File file) {
        execute0();
        try {
            document.save(file);
        } catch (IOException e) {
            throw new PdfWatermarkException(e);
        }
    }

    private void execute0() {
        checkParam();
        for (PDPage page : document.getPages()) {
            if (config.isIgnoreRotation()) {
                ignoreRotation(page);
            }
            addWatermark(page);
        }
    }

    private void addWatermark(PDPage page) {
        try (PDPageContentStream stream = new PDPageContentStream(document, page,
                PDPageContentStream.AppendMode.APPEND, true, true)) {
            initGraphics(stream);

            PDRectangle mediaBox = page.getMediaBox();
            Point topLeftCornerPoint = new Point(mediaBox.getLowerLeftX(), mediaBox.getLowerLeftY());
            Point bottomRightCornerPoint = new Point(mediaBox.getUpperRightX(), mediaBox.getUpperRightY());
            WatermarkParam watermarkParam = calculate.calculateLocation(topLeftCornerPoint, bottomRightCornerPoint,
                    this, watermark);
            Matrix matrix = Matrix.getRotateInstance(Math.toRadians(config.getAngle()), 0, 0);

            stream.transform(matrix);
            stream.beginText();
            stream.newLineAtOffset((float) watermarkParam.getX(), (float) watermarkParam.getY());
            stream.showText(watermark);
            stream.endText();
            stream.restoreGraphicsState();
        } catch (IOException e) {
            throw new PdfWatermarkException(e);
        }
    }

    @Override
    protected void initGraphics(PDPageContentStream graphics) throws IOException {
        setGraphicsState(graphics);
        graphics.setNonStrokingColor(config.getColor());
        graphics.setFont(font, (float) config.getFontSize());

    }

    private void setGraphicsState(PDPageContentStream stream) throws IOException {
        PDExtendedGraphicsState gs = new PDExtendedGraphicsState();
        gs.setNonStrokingAlphaConstant((float) config.getAlpha());
        gs.setAlphaSourceFlag(true);
        gs.setBlendMode(BlendMode.MULTIPLY);
        stream.setGraphicsStateParameters(gs);
    }

    /**
     * Ignore pdf page rotation.
     * The displayed style will be used to add watermark.
     *
     * @param page pdf page
     */
    private void ignoreRotation(PDPage page) {
        int rotation = page.getRotation();
        page.setRotation(0);
        try (PDPageContentStream cs = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.PREPEND,
                false, false)) {
            Matrix matrix = Matrix.getRotateInstance(Math.toRadians(360 - rotation), 0, 0);
            cs.transform(matrix);
            PDRectangle cropBox = page.getCropBox();
            Rectangle rectangle = cropBox.transform(matrix).getBounds();
            PDRectangle newBox = new PDRectangle((float) rectangle.getX(), (float) rectangle.getY(),
                    (float) rectangle.getWidth(), (float) rectangle.getHeight());
            page.setCropBox(newBox);
            page.setMediaBox(newBox);
        } catch (IOException e) {
            throw new PdfWatermarkException(e);
        }
    }

    @Override
    public AbstractWatermarkHandler<PDFont, PDPageContentStream> watermark(String watermarkText) {
        this.watermark = watermarkText;
        return this;
    }

    @Override
    public void close() throws IOException {
        document.close();
    }
}
