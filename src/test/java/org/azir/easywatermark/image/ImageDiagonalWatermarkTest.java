package org.azir.easywatermark.image;

import org.azir.easywatermark.AbstractTest;
import org.azir.easywatermark.core.EasyWatermark;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.enums.DiagonalDirectionTypeEnum;
import org.azir.easywatermark.enums.EasyWatermarkTypeEnum;
import org.junit.Test;

/**
 * @author zhangshukun
 * @date 2024/4/20
 */
public class ImageDiagonalWatermarkTest extends AbstractTest {

    private static final String TYPE = "jpeg";

    private static final String DIR = "image/diagonal";

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
                .easyWatermarkType(EasyWatermarkTypeEnum.DIAGONAL);
    }

    @Test
    public void testDiagonalTopToBottomSingleWatermark() {
        WATERMARK_CONFIG.setDiagonalDirectionType(DiagonalDirectionTypeEnum.TOP_TO_BOTTOM);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testDiagonalBottomToTopSingleWatermark() {
        WATERMARK_CONFIG.setDiagonalDirectionType(DiagonalDirectionTypeEnum.BOTTOM_TO_TOP);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testDiagonalTopToBottomMultiWatermark() {
        WATERMARK_CONFIG.setDiagonalDirectionType(DiagonalDirectionTypeEnum.TOP_TO_BOTTOM);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错", "明天天气也不错", "后天天气也不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testDiagonalBottomToTopMultiWatermark() {
        WATERMARK_CONFIG.setDiagonalDirectionType(DiagonalDirectionTypeEnum.BOTTOM_TO_TOP);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错", "明天天气也不错", "后天天气也不错")
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testDiagonalTopToBottomImageWatermark() {
        WATERMARK_CONFIG.setDiagonalDirectionType(DiagonalDirectionTypeEnum.TOP_TO_BOTTOM);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testDiagonalBottomToTopImageWatermark() {
        WATERMARK_CONFIG.setDiagonalDirectionType(DiagonalDirectionTypeEnum.BOTTOM_TO_TOP);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        // 输出到桌面
        saveOutPutFile(executor, DIR, TYPE);
    }


}
