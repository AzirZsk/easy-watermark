package org.easywatermark.word.docx;

import org.easywatermark.AbstractDiagonalWatermarkTest;
import org.easywatermark.core.EasyWatermark;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.enums.EasyWatermarkTypeEnum;

import java.awt.*;

/**
 * @author zhangshukun
 * @since 2024/07/30
 */
public class DocxDiagonalWatermarkTest extends AbstractDiagonalWatermarkTest {

    static {
        type = "docx";
        dir = type + "/diagonal";
        fontConfig = new FontConfig();
        fontConfig.setFontFile(getFile("STZHONGS.TTF"));

        watermarkConfig = new WatermarkConfig();
        watermarkConfig.setAlpha(0.5f);
        watermarkConfig.setColor(Color.RED);

        easyWatermark = EasyWatermark.create()
                .file(getFile("test.docx"))
                .config(fontConfig)
                .config(watermarkConfig)
                .easyWatermarkType(EasyWatermarkTypeEnum.DIAGONAL);
    }
}
