package org.easywatermark.word.docx;

import org.easywatermark.AbstractCenterWatermarkTest;
import org.easywatermark.core.EasyWatermark;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.enums.EasyWatermarkTypeEnum;

import java.awt.*;

/**
 * @author zhangshukun
 * @since 2024/07/17
 */
public class DocxCenterWatermarkTest extends AbstractCenterWatermarkTest {

    static {
        type = "docx";
        dir = type + "/center";
        fontConfig = new FontConfig();
        fontConfig.setFontFile(getFile("STZHONGS.TTF"));

        watermarkConfig = new WatermarkConfig();
        watermarkConfig.setAlpha(0.5f);
        watermarkConfig.setColor(Color.RED);

        easyWatermark = EasyWatermark.create()
                .file(getFile("test.docx"))
                .config(fontConfig)
                .config(watermarkConfig)
                .easyWatermarkType(EasyWatermarkTypeEnum.CENTER);
    }

}
