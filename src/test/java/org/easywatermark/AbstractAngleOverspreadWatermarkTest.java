package org.easywatermark;

import org.easywatermark.enums.OverspreadTypeEnum;
import org.junit.Test;

/**
 * @author zhangshukun
 * @date 2024/7/27
 */
public abstract class AbstractAngleOverspreadWatermarkTest extends AbstractTest {

    @Test
    public void test30AngleLowOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(30);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test30AngleLowOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(30);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test30AngleLowOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(30);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test30AngleNormalOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(30);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test30AngleNormalOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(30);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test30AngleNormalOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(30);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test30AngleHighOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(30);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test30AngleHighOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(30);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test30AngleHighOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(30);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test60AngleLowOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(60);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test60AngleLowOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(60);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test60AngleLowOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(60);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test60AngleNormalOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(60);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test60AngleNormalOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(60);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test60AngleNormalOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(60);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test60AngleHighOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(60);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test60AngleHighOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(60);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test60AngleHighOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(60);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test90AngleLowOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(90);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test90AngleLowOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(90);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test90AngleLowOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(90);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test90AngleNormalOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(90);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test90AngleNormalOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(90);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test90AngleNormalOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(90);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test90AngleHighOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(90);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test90AngleHighOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(90);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test90AngleHighOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(90);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test120AngleLowOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(120);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test120AngleLowOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(120);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test120AngleLowOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(120);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test120AngleNormalOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(120);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test120AngleNormalOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(120);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test120AngleNormalOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(120);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test120AngleHighOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(120);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test120AngleHighOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(120);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test120AngleHighOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(120);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test150AngleLowOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(150);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test150AngleLowOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(150);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错AaBbCc")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test150AngleLowOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(150);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test150AngleNormalOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(150);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test150AngleNormalOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(150);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test150AngleNormalOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(150);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test150AngleHighOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(150);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test150AngleHighOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(150);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test150AngleHighOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(150);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test180AngleLowOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(180);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test180AngleLowOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(180);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test180AngleLowOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.LOW);
        watermarkConfig.setAngle(180);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test180AngleNormalOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(180);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test180AngleNormalOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(180);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test180AngleNormalOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.NORMAL);
        watermarkConfig.setAngle(180);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test180AngleHighOverspreadSingleWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(180);
        byte[] executor = easyWatermark.text("今天天气真不错AaBbCcGgQq")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test180AngleHighOverspreadMultiWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(180);
        byte[] executor = easyWatermark.text("今天天气真不错Aa", "明天天气真不错AaBb", "后天天气真不错")
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

    @Test
    public void test180AngleHighOverspreadImageWatermark() {
        watermarkConfig.setOverspreadType(OverspreadTypeEnum.HIGH);
        watermarkConfig.setAngle(180);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
        watermarkConfig.setAngle(0);
    }

}
