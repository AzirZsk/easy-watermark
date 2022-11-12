package org.azir.allwatermark.core;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.azir.allwatermark.entity.WatermarkParam;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Use apache pdfbox dependency.
 *
 * @author Azir
 * @date 2022/11/12
 */
public class PdfWatermarkHandler extends AbstractWatermarkHandler {

    @Override
    public OutputStream addWatermark(InputStream inputStream, WatermarkParam param) throws IOException {
        PDDocument document = PDDocument.load(inputStream);
        document.setAllSecurityToBeRemoved(true);

        return null;
    }

}
