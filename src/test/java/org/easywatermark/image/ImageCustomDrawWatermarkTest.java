package org.easywatermark.image;

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
public class ImageCustomDrawWatermarkTest extends AbstractTest {

    private static final String TYPE = "jpeg";

    private static final String DIR = "image/custom";

    private static final EasyWatermark EASY_WATERMARK;

    private static final FontConfig FONT_CONFIG;

    private static final WatermarkConfig WATERMARK_CONFIG;

    static {
        FONT_CONFIG = new FontConfig();

        WATERMARK_CONFIG = new WatermarkConfig();
        WATERMARK_CONFIG.setAlpha(0.5f);

        EASY_WATERMARK = EasyWatermark.create()
                .file(getFile("600-400.png"))
                .config(FONT_CONFIG)
                .config(WATERMARK_CONFIG)
                .easyWatermarkType(EasyWatermarkTypeEnum.CUSTOM);
    }

    @Test
    public void testCustomDrawWatermark() {
        byte[] executor = EASY_WATERMARK.customDraw((pageInfo, graphicsProvider, fontProvider) -> {
            log.info("pageInfo: {}", pageInfo);
            int pageNo = pageInfo.getPageNo();
            graphicsProvider.drawString(300, 20, "asdfasdfasdfzxdvzxcv", pageNo);
            log.info("graphicsProvider: {}", graphicsProvider);
        }).executor();
        saveOutPutFile(executor, DIR, TYPE);
    }

}
