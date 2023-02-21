package org.azir.easywatermark.core.handler;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.azir.easywatermark.core.AbstractWatermarkHandler;
import org.azir.easywatermark.entity.WatermarkParam;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Use apache pdfbox dependency to pdf add watermark.
 *
 * @author Azir
 * @date 2022/11/12
 */
public class PdfWatermarkHandler extends AbstractWatermarkHandler<PDFont> {

    private PDDocument document;

    public PdfWatermarkHandler(byte[] data) {

    }

    @Override
    public PDFont loadFont(WatermarkParam param) {
        return null;
    }

    @Override
    public double getStringWidth(String text) {
        return 0;
    }

    @Override
    public OutputStream execute() {
        return null;
    }

    @SneakyThrows
    @Override
    public void load(byte[] data) {
        document = PDDocument.load(data);
    }

    @Override
    public void close() throws IOException {

    }
}
