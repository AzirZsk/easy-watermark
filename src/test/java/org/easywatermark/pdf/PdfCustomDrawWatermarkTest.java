package org.easywatermark.pdf;

import lombok.extern.slf4j.Slf4j;
import org.easywatermark.AbstractCustomDrawWatermarkTest;
import org.easywatermark.core.EasyWatermark;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.enums.EasyWatermarkTypeEnum;

/**
 * @author zhangshukun
 * @date 2024/5/1
 */
@Slf4j
public class PdfCustomDrawWatermarkTest extends AbstractCustomDrawWatermarkTest {

    static {
        type = "pdf";
        dir = type + "/custom";

        fontConfig = new FontConfig();
        fontConfig.setFontFile(getFile("STZHONGS.TTF"));

        watermarkConfig = new WatermarkConfig();
        watermarkConfig.setAlpha(0.5f);

        easyWatermark = EasyWatermark.create()
                .file(getFile("test-pdf.pdf"))
                .config(fontConfig)
                .config(watermarkConfig)
                .easyWatermarkType(EasyWatermarkTypeEnum.CUSTOM);
    }

}