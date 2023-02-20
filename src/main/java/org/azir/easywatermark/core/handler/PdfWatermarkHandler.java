package org.azir.easywatermark.core.handler;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.azir.easywatermark.constant.WatermarkConstant;
import org.azir.easywatermark.core.AbstractWatermarkHandler;
import org.azir.easywatermark.entity.WatermarkParam;
import org.azir.easywatermark.exception.EasyWatermarkException;
import org.azir.easywatermark.exception.PdfWatermarkException;
import org.azir.easywatermark.utils.WatermarkParamUtils;

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

    @Override
    public OutputStream addWatermark(InputStream inputStream, WatermarkParam param) throws IOException {
        WatermarkParamUtils.checkParam(param);
        try {
            PDDocument document = PDDocument.load(inputStream);
            DOCUMENT_THREAD_LOCAL.set(document);
            document.setAllSecurityToBeRemoved(true);
            for (PDPage page : document.getPages()) {
                handlerPdfPage(page, param);
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
     * @param page     pdf page
     * @param param    watermark param
     */
    private void handlerPdfPage(PDPage page, WatermarkParam param) throws IOException {
        PDPageContentStream stream =
                new PDPageContentStream(DOCUMENT_THREAD_LOCAL.get(), page, PDPageContentStream.AppendMode.APPEND, true, true);

        PDExtendedGraphicsState r = new PDExtendedGraphicsState();
        r.setNonStrokingAlphaConstant((float) param.getTransparency());
        r.setAlphaSourceFlag(true);
        stream.setGraphicsStateParameters(r);

        PDRectangle mediaBox = page.getMediaBox();
        float pdfHeight = mediaBox.getHeight();
        float pdfWidth = mediaBox.getWidth();

        PDFont font = getFont(param);
        int fontSize = param.getFontSize();
        float textWidth = font.getStringWidth(param.getText()) / WatermarkConstant.FONT_SCALE * fontSize;


        stream.setFont(font, fontSize);
        stream.setNonStrokingColor(param.getFontColor());
    }

    private void clearThreadLocal() {
        DOCUMENT_THREAD_LOCAL.remove();
        fontThreadLocal.remove();
    }

    @Override
    public PDFont getFont(WatermarkParam param) {
        PDDocument document = DOCUMENT_THREAD_LOCAL.get();
        if (document == null) {
            throw new PdfWatermarkException("Handler pdf document thread local is null");
        }
        PDFont font = fontThreadLocal.get();
        if (font != null) {
            return font;
        }
        try {
            font = PDType0Font.load(document, getClass().getResourceAsStream(param.getFontFilePath()));
            fontThreadLocal.set(font);
            return font;
        } catch (Exception e) {
            throw new PdfWatermarkException(e.getMessage(), e);
        }
    }

    @Override
    public double getStringWidth(String text) {
        PDFont font = fontThreadLocal.get();
        try {
            return font.getStringWidth(text);
        } catch (IOException e) {
            throw new EasyWatermarkException(e.getMessage(), e);
        }
    }
}
