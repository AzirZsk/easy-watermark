package org.azir.easywatermark.pdf;

import org.azir.easywatermark.AbstractTest;
import org.azir.easywatermark.core.EasyWatermark;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.enums.EasyWatermarkTypeEnum;
import org.azir.easywatermark.enums.OverspreadTypeEnum;
import org.junit.Test;

/**
 * @author zhangshukun
 * @date 2024/4/25
 */
public class PdfOverspreadWatermarkTest extends AbstractTest {

    private static final String TYPE = "pdf";

    private static final String DIR = TYPE + "/overspread";

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
                .easyWatermarkType(EasyWatermarkTypeEnum.OVERSPREAD);
    }

    @Test
    public void testLowOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testNormalOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testHighOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testLowOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testNormalOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testHighOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testLowOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testNormalOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testHighOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
    }
}
