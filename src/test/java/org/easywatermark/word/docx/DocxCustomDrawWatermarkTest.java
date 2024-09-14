package org.easywatermark.word.docx;

import org.easywatermark.AbstractCustomDrawWatermarkTest;
import org.easywatermark.core.EasyWatermark;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.enums.EasyWatermarkTypeEnum;

import java.awt.*;

/**
 * @author zhangshukun
 * @date 2024/8/2
 */
public class DocxCustomDrawWatermarkTest extends AbstractCustomDrawWatermarkTest {

    static {
        type = "docx";
        dir = type + "/custom";
        fontConfig = new FontConfig();
        fontConfig.setFontFile(getFile("STZHONGS.TTF"));

        watermarkConfig = new WatermarkConfig();
        watermarkConfig.setAlpha(0.5f);
        watermarkConfig.setColor(Color.RED);

        easyWatermark = EasyWatermark.create()
                .file(getFile("test.docx"))
                .config(fontConfig)
                .config(watermarkConfig)
                .easyWatermarkType(EasyWatermarkTypeEnum.CUSTOM);
    }
}
