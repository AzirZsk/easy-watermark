package org.azir.easywatermark.pdf;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;
import org.azir.easywatermark.AbstractTest;
import org.azir.easywatermark.core.EasyWatermark;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.enums.DiagonalDirectionTypeEnum;
import org.azir.easywatermark.enums.EasyWatermarkTypeEnum;
import org.junit.Test;

import java.awt.*;
import java.io.ByteArrayOutputStream;

/**
 * @author zhangshukun
 * @since 2024/04/21
 */
public class PdfDiagonalWatermarkTest extends AbstractTest {

    private static final String TYPE = "pdf";

    private static final String DIR = TYPE;

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
        byte[] executor = EASY_WATERMARK.text("AaBbCcGgQq今天天气真不错AaBbCcGgQq", "ZzXxIiUuYy明天天气真不错AaBbCcGgQqCcVv", "ZzXxIiUuYy明天天气真不错AaBbCcGgQqCcVv")
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
