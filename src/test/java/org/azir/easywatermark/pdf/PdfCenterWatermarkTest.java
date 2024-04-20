package org.azir.easywatermark.pdf;

import org.azir.easywatermark.AbstractTest;
import org.azir.easywatermark.core.EasyWatermark;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.enums.CenterLocationTypeEnum;
import org.azir.easywatermark.enums.EasyWatermarkTypeEnum;
import org.junit.Test;

/**
 * @author zhangshukun
 * @date 2024/4/20
 */
public class PdfCenterWatermarkTest extends AbstractTest {

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
                .easyWatermarkType(EasyWatermarkTypeEnum.CENTER);
    }

    @Test
    public void testVerticalCenterSingleWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.VERTICAL_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, "pdf", "pdf");
    }

    @Test
    public void testTopCenterSingleWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.TOP_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, "pdf", "pdf");
    }

    @Test
    public void testBottomCenterSingleWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.BOTTOM_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, "pdf", "pdf");
    }

    @Test
    public void testLeftCenterSingleWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.LEFT_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, "pdf", "pdf");
    }

    @Test
    public void testRightCenterSingleWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.RIGHT_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, "pdf", "pdf");
    }

    @Test
    public void testVerticalCenterMultiWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.VERTICAL_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错", "明天天气也不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, "pdf", "pdf");
    }

    @Test
    public void testTopCenterMultiWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.TOP_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错", "明天天气也不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, "pdf", "pdf");
    }

    @Test
    public void testBottomCenterMultiWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.BOTTOM_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错", "明天天气也不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, "pdf", "pdf");
    }

    @Test
    public void testLeftCenterMultiWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.LEFT_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错", "明天天气也不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, "pdf", "pdf");
    }

    @Test
    public void testRightCenterMultiWatermark() {
        WATERMARK_CONFIG.setCenterLocationType(CenterLocationTypeEnum.RIGHT_CENTER);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错", "明天天气也不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, "pdf", "pdf");
    }
}
