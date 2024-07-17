package org.easywatermark.word.docx;

import org.easywatermark.AbstractTest;
import org.easywatermark.core.EasyWatermark;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.enums.CenterLocationTypeEnum;
import org.easywatermark.enums.EasyWatermarkTypeEnum;
import org.junit.Test;

import java.awt.*;

/**
 * @author zhangshukun
 * @since 2024/07/17
 */
public class DocxCenterWatermarkTest extends AbstractTest {

    private static final String TYPE = "docx";

    private static final String DIR = TYPE + "/center";

    private static final EasyWatermark EASY_WATERMARK;

    private static final FontConfig FONT_CONFIG;

    private static final WatermarkConfig WATERMARK_CONFIG;

    static {
        FONT_CONFIG = new FontConfig();
        FONT_CONFIG.setFontFile(getFile("STZHONGS.TTF"));

        WATERMARK_CONFIG = new WatermarkConfig();
        WATERMARK_CONFIG.setAlpha(0.5f);
        WATERMARK_CONFIG.setColor(Color.RED);

        EASY_WATERMARK = EasyWatermark.create()
                .file(getFile("test.docx"))
                .config(FONT_CONFIG)
                .config(WATERMARK_CONFIG)
                .easyWatermarkType(EasyWatermarkTypeEnum.CENTER);
    }

    @Test
    public void testVerticalCenterSingleWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.VERTICAL_CENTER);
        byte[] executor = EASY_WATERMARK.text("AaCcGgQqJjMmPp")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }
}
