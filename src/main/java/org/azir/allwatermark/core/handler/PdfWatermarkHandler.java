package org.azir.allwatermark.core.handler;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.azir.allwatermark.core.AbstractWatermarkHandler;
import org.azir.allwatermark.entity.WatermarkParam;

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

    @Override
    public OutputStream addWatermark(InputStream inputStream, WatermarkParam param) throws IOException {
        PDDocument document = PDDocument.load(inputStream);
        document.setAllSecurityToBeRemoved(true);
        for (PDPage page : document.getPages()) {
            handlerPdfPage(document, page, param);
        }
        OutputStream res = new ByteArrayOutputStream();
        document.save(res);
        return res;
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

    @Override
    public PDFont getFont(WatermarkParam param) {
        return null;
    }
}
