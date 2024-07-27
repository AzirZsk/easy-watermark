package org.easywatermark;

import org.easywatermark.enums.OverspreadTypeEnum;
import org.junit.Test;

/**
 * @author zhangshukun
 * @date 2024/7/27
 */
public abstract class AbstractOverspreadWatermarkTest extends AbstractTest {

    @Test
    public void testLowOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testNormalOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testHighOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testLowOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testNormalOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testHighOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testLowOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testNormalOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testHighOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
    }
}
