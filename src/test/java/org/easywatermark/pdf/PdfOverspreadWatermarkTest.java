package org.easywatermark.pdf;

import org.easywatermark.AbstractOverspreadWatermarkTest;
import org.easywatermark.core.EasyWatermark;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.enums.EasyWatermarkTypeEnum;

/**
 * @author zhangshukun
 * @date 2024/4/25
 */
public class PdfOverspreadWatermarkTest extends AbstractOverspreadWatermarkTest {

    static {
        type = "pdf";
        dir = type + "/overspread";

        fontConfig = new FontConfig();
        fontConfig.setFontFile(getFile("STZHONGS.TTF"));

        watermarkConfig = new WatermarkConfig();
        watermarkConfig.setAlpha(0.5f);

        easyWatermark = EasyWatermark.create()
                .file(getFile("test-pdf.pdf"))
                .config(fontConfig)
                .config(watermarkConfig)
                .easyWatermarkType(EasyWatermarkTypeEnum.OVERSPREAD);
    }

}
