package org.easywatermark.pdf;

import lombok.extern.slf4j.Slf4j;
import org.easywatermark.AbstractTest;
import org.easywatermark.core.EasyWatermark;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.enums.EasyWatermarkTypeEnum;
import org.junit.Test;

/**
 * @author zhangshukun
 * @date 2024/5/1
 */
@Slf4j
public class PdfCustomDrawWatermarkTest extends AbstractTest {

    private static final String TYPE = "pdf";

    private static final String DIR = TYPE + "/custom";

    private static final EasyWatermark EASY_WATERMARK;

    private static final FontConfig FONT_CONFIG;

    private static final WatermarkConfig WATERMARK_CONFIG;

    static {
        FONT_CONFIG = new FontConfig();
        FONT_CONFIG.setFontFile(getFile("STZHONGS.TTF"));

        WATERMARK_CONFIG = new WatermarkConfig();
        WATERMARK_CONFIG.setAlpha(0.5f);

        EASY_WATERMARK = EasyWatermark.create()
                .file(getFile("test-pdf.pdf"))
                .config(FONT_CONFIG)
                .config(WATERMARK_CONFIG)
                .easyWatermarkType(EasyWatermarkTypeEnum.CUSTOM);
    }

    @Test
    public void testDrawSingleWatermarkText() {
        byte[] executor = EASY_WATERMARK.customDraw((pageInfo, graphicsProvider, fontProvider) -> {
            int pageNo = pageInfo.getPageNo();
            graphicsProvider.drawString(300, 20, "asdfasdfasdfzxdvzxcv", pageNo);
            log.info("graphicsProvider: {}", graphicsProvider);
        }).executor();
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testOddPageWatermarkTest() {
        byte[] executor = EASY_WATERMARK.customDraw((pageInfo, graphicsProvider, fontProvider) -> {
            int pageNo = pageInfo.getPageNo();
            if (pageNo % 2 == 1) {
                graphicsProvider.drawString(300, 20, "asdfasdfasdfzxdvzxcv", pageNo);
            }
            log.info("graphicsProvider: {}", graphicsProvider);
        }).executor();
        saveOutPutFile(executor, DIR, TYPE);
    }
}