package org.easywatermark.pdf;

import org.easywatermark.AbstractTest;
import org.easywatermark.core.EasyWatermark;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.enums.DiagonalDirectionTypeEnum;
import org.easywatermark.enums.EasyWatermarkTypeEnum;
import org.junit.Test;

/**
 * @author zhangshukun
 * @since 2024/04/21
 */
public class PdfDiagonalWatermarkTest extends AbstractTest {

    private static final String TYPE = "pdf";

    private static final String DIR = TYPE + "/diagonal";

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
                .easyWatermarkType(EasyWatermarkTypeEnum.DIAGONAL);
    }

    @Test
    public void testTopToBottomDiagonalSingleWatermark() {
        WATERMARK_CONFIG.setDiagonalDirectionType(DiagonalDirectionTypeEnum.TOP_TO_BOTTOM);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();


        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testBottomToTopDiagonalSingleWatermark() {
        WATERMARK_CONFIG.setDiagonalDirectionType(DiagonalDirectionTypeEnum.BOTTOM_TO_TOP);
        byte[] executor = EASY_WATERMARK.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testTopToBottomDiagonalMultiWatermark() {
        WATERMARK_CONFIG.setDiagonalDirectionType(DiagonalDirectionTypeEnum.TOP_TO_BOTTOM);
        byte[] executor = EASY_WATERMARK.text("AaBbCcGgQq今天天气真不错AaBbCcGgQq", "ZzXxIiUuYy明天天气真不错AaBbCcGgQqCcVv", "ZzXxIiUuYy明天天气真不错AaBbCcGgQqCcVv")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testBottomToTopDiagonalMultiWatermark() {
        WATERMARK_CONFIG.setDiagonalDirectionType(DiagonalDirectionTypeEnum.BOTTOM_TO_TOP);
        byte[] executor = EASY_WATERMARK.text("AaBbCcGgQq今天天气真不错AaBbCcGgQq", "ZzXxIiUuYy明天天气真不错AaBbCcGgQqCcVv", "ZzXxIiUuYy明天天气真不错AaBbCcGgQqCcVv", "ZzXxIiUuYy明天天气真不错AaBbCcGgQqCcVv")
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testTopToBottomDiagonalImageWatermark() {
        WATERMARK_CONFIG.setDiagonalDirectionType(DiagonalDirectionTypeEnum.TOP_TO_BOTTOM);
        byte[] executor = EASY_WATERMARK.image(getByte("50-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
    }

    @Test
    public void testBottomToTopDiagonalImageWatermark() {
        WATERMARK_CONFIG.setDiagonalDirectionType(DiagonalDirectionTypeEnum.BOTTOM_TO_TOP);
        byte[] executor = EASY_WATERMARK.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, DIR, TYPE);
    }
}
