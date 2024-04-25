package org.azir.easywatermark.image;

import org.azir.easywatermark.AbstractTest;
import org.azir.easywatermark.core.EasyWatermark;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.enums.EasyWatermarkTypeEnum;
import org.azir.easywatermark.enums.OverspreadTypeEnum;
import org.junit.Test;

/**
 * @author zhangshukun
 * @date 2024/4/20
 */
public class ImageOverspreadWatermarkTest extends AbstractTest {

    private static final String TYPE = "jpeg";

    private static final String DIR = "image";

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
                .easyWatermarkType(EasyWatermarkTypeEnum.OVERSPREAD);
    }

    @Test
    public void testLowOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testNormalOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testHighOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testLowOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错", "明天天气也不错", "后天天气也不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testNormalOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错", "明天天气也不错", "后天天气也不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testHighOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错", "明天天气也不错", "后天天气也不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testLowOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        byte[] executor = EASY_WATERMARK.image(getByte("50-50-blue.png"))
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testNormalOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        byte[] executor = EASY_WATERMARK.image(getByte("50-50-blue.png"))
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testHighOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        byte[] executor = EASY_WATERMARK.image(getByte("50-50-blue.png"))
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testAngleLowOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testAngleNormalOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void testAngleHighOverspreadSingleWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void testAngleLowOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气也不错AaBb", "后天天气也不错AaBbCc")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void testAngleNormalOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(-30);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气也不错AaBb", "后天天气也不错AaBbCc")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void testAngleHighOverspreadMultiWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错Aa", "明天天气也不错AaBb", "后天天气也不错AaBbCc")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void testAngleLowOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.LOW);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void testAngleNormalOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.NORMAL);
        WATERMARK_CONFIG.setAngle(-30);
        byte[] executor = EASY_WATERMARK.image(getByte("50-50-blue.png"))
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

    @Test
    public void testAngleHighOverspreadImageWatermark() {
        WATERMARK_CONFIG.setOverspreadType(OverspreadTypeEnum.HIGH);
        WATERMARK_CONFIG.setAngle(30);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
        WATERMARK_CONFIG.setAngle(0);
    }

}
