package org.azir.allwatermark.core.handler;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.azir.allwatermark.core.AbstractWatermarkHandler;
import org.azir.allwatermark.entity.WatermarkParam;
import org.azir.allwatermark.exception.PdfWatermarkException;

import java.io.ByteArrayOutputStream;
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

    private static final ThreadLocal<PDDocument> DOCUMENT_THREAD_LOCAL = new ThreadLocal<>();

    private static final ThreadLocal<PDFont> FONT_THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public OutputStream addWatermark(InputStream inputStream, WatermarkParam param) throws IOException {
        try {
            PDDocument document = PDDocument.load(inputStream);
            DOCUMENT_THREAD_LOCAL.set(document);
            document.setAllSecurityToBeRemoved(true);
            for (PDPage page : document.getPages()) {
                handlerPdfPage(document, page, param);
            }
            OutputStream res = new ByteArrayOutputStream();
            document.save(res);
            return res;
        } finally {
            clearThreadLocal();
        }
    }

    /**
     * pdf page to add watermark.
     *
     * @param document pdf word
     * @param page     pdf page
     * @param param    watermark param
     */
    private void handlerPdfPage(PDDocument document, PDPage page, WatermarkParam param) throws IOException {

    }

    private void clearThreadLocal() {
        DOCUMENT_THREAD_LOCAL.remove();
        FONT_THREAD_LOCAL.remove();
    }

    @Override
    public PDFont getFont(WatermarkParam param) {
        PDDocument document = DOCUMENT_THREAD_LOCAL.get();
        if (document == null) {
            throw new PdfWatermarkException("Handler pdf document thread local is null");
        }
        PDFont font = FONT_THREAD_LOCAL.get();
        if (font != null) {
            return font;
        }
        try {
            font = PDType0Font.load(document, getClass().getResourceAsStream(param.getFontFileUrl()));
            FONT_THREAD_LOCAL.set(font);
            return font;
        } catch (IOException e) {
            throw new PdfWatermarkException(e.getMessage(), e);
        }
    }
}
