package org.easywatermark;

import org.easywatermark.enums.DiagonalDirectionTypeEnum;
import org.junit.Test;

/**
 * @author zhangshukun
 * @date 2024/7/27
 */
public abstract class AbstractDiagonalWatermarkTest extends AbstractTest {

    @Test
    public void testDiagonalTopToBottomSingleWatermark() {
        watermarkConfig.setDiagonalDirectionType(DiagonalDirectionTypeEnum.TOP_TO_BOTTOM);
        byte[] executor = easyWatermark.text("今天天气真不错")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testDiagonalBottomToTopSingleWatermark() {
        watermarkConfig.setDiagonalDirectionType(DiagonalDirectionTypeEnum.BOTTOM_TO_TOP);
        byte[] executor = easyWatermark.text("今天天气真不错")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testDiagonalTopToBottomMultiWatermark() {
        watermarkConfig.setDiagonalDirectionType(DiagonalDirectionTypeEnum.TOP_TO_BOTTOM);
        byte[] executor = easyWatermark.text("今天天气真不错", "明天天气也不错", "后天天气也不错")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testDiagonalBottomToTopMultiWatermark() {
        watermarkConfig.setDiagonalDirectionType(DiagonalDirectionTypeEnum.BOTTOM_TO_TOP);
        byte[] executor = easyWatermark.text("今天天气真不错", "明天天气也不错", "后天天气也不错")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testDiagonalTopToBottomImageWatermark() {
        watermarkConfig.setDiagonalDirectionType(DiagonalDirectionTypeEnum.TOP_TO_BOTTOM);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testDiagonalBottomToTopImageWatermark() {
        watermarkConfig.setDiagonalDirectionType(DiagonalDirectionTypeEnum.BOTTOM_TO_TOP);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
    }
}
