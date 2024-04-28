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
 * @since 2024/04/28
 */
public class PdfAngleOverspreadWatermarkTest extends AbstractTest {

    private static final String TYPE = "pdf";

    private static final String DIR = TYPE + "/overspread/angle";

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
    public void test30AngleLowOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test30AngleLowOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test30AngleLowOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test30AngleNormalOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test30AngleNormalOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test30AngleNormalOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test30AngleHighOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test30AngleHighOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test30AngleHighOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test60AngleLowOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(60);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test60AngleLowOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(60);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test60AngleLowOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(60);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test60AngleNormalOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(60);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test60AngleNormalOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(60);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test60AngleNormalOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(60);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test60AngleHighOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(60);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test60AngleHighOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(60);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test60AngleHighOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(60);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test90AngleLowOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(90);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test90AngleLowOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(90);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test90AngleLowOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(90);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test90AngleNormalOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(90);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test90AngleNormalOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(90);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test90AngleNormalOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(90);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test90AngleHighOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(90);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test90AngleHighOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(90);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test90AngleHighOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(90);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test120AngleLowOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(120);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test120AngleLowOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(120);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test120AngleLowOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(120);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test120AngleNormalOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(120);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test120AngleNormalOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(120);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test120AngleNormalOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(120);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test120AngleHighOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(120);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test120AngleHighOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(120);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test120AngleHighOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(120);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test150AngleLowOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(150);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test150AngleLowOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(150);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test150AngleLowOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(150);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test150AngleNormalOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(150);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test150AngleNormalOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(150);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test150AngleNormalOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(150);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test150AngleHighOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(150);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test150AngleHighOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(150);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test150AngleHighOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(150);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test180AngleLowOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(180);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test180AngleLowOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(180);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test180AngleLowOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(180);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test180AngleNormalOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(180);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test180AngleNormalOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(180);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test180AngleNormalOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(180);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test180AngleHighOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(180);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test180AngleHighOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(180);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void test180AngleHighOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(180);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }


}
