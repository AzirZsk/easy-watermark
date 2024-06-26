package org.easywatermark.image;

import lombok.extern.slf4j.Slf4j;
import org.easywatermark.AbstractTest;
import org.easywatermark.core.EasyWatermark;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.enums.CenterLocationTypeEnum;
import org.easywatermark.enums.EasyWatermarkTypeEnum;
import org.junit.Test;

/**
 * @author Azir
 * @date 2022/11/13
 */
@Slf4j
public class ImageCenterWatermarkTest extends AbstractTest {

    private static final String TYPE = "jpeg";

    private static final String DIR = "image/center";

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
                .easyWatermarkType(EasyWatermarkTypeEnum.CENTER);
    }

    @Test
    public void testVerticalCenterSingleWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.VERTICAL_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testTopCenterSingleWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.TOP_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testBottomCenterSingleWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.BOTTOM_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testLeftCenterSingleWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.LEFT_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testRightCenterSingleWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.RIGHT_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testVerticalCenterMultiWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.VERTICAL_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错", "明天天气也不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testTopCenterMultiWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.TOP_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错", "明天天气也不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testBottomCenterMultiWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.BOTTOM_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错", "明天天气也不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testLeftCenterMultiWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.LEFT_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错", "明天天气也不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testRightCenterMultiWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.RIGHT_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错", "明天天气也不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testVerticalCenterImageWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.VERTICAL_CENTER);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testTopCenterImageWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.TOP_CENTER);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testBottomCenterImageWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.BOTTOM_CENTER);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testLeftCenterImageWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.LEFT_CENTER);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testRightCenterImageWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.RIGHT_CENTER);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

}
