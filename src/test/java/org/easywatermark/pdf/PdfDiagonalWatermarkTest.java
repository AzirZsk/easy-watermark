package org.easywatermark.pdf;

import org.easywatermark.AbstractDiagonalWatermarkTest;
import org.easywatermark.core.EasyWatermark;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.enums.EasyWatermarkTypeEnum;

/**
 * @author zhangshukun
 * @since 2024/04/21
 */
public class PdfDiagonalWatermarkTest extends AbstractDiagonalWatermarkTest {

    static {
        type = "pdf";
        dir = type + "/diagonal";

        fontConfig = new FontConfig();
        fontConfig.setFontFile(getFile("STZHONGS.TTF"));

        watermarkConfig = new WatermarkConfig();
        watermarkConfig.setAlpha(0.5f);

        easyWatermark = EasyWatermark.create()
                .file(getFile("test-pdf.pdf"))
                .config(fontConfig)
                .config(watermarkConfig)
                .easyWatermarkType(EasyWatermarkTypeEnum.DIAGONAL);
    }

}
