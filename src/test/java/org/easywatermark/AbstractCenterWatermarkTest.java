package org.easywatermark;

import org.easywatermark.enums.CenterLocationTypeEnum;
import org.junit.Test;

/**
 * @author zhangshukun
 * @date 2024/7/27
 */
public abstract class AbstractCenterWatermarkTest extends AbstractTest {

    @Test
    public void testVerticalCenterSingleWatermark() {
        watermarkConfig.setCenterLocationType(CenterLocationTypeEnum.VERTICAL_CENTER);
        byte[] executor = easyWatermark.text("今天天气真不错")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testTopCenterSingleWatermark() {
        watermarkConfig.setCenterLocationType(CenterLocationTypeEnum.TOP_CENTER);
        byte[] executor = easyWatermark.text("今天天气真不错")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testBottomCenterSingleWatermark() {
        watermarkConfig.setCenterLocationType(CenterLocationTypeEnum.BOTTOM_CENTER);
        byte[] executor = easyWatermark.text("今天天气真不错")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testLeftCenterSingleWatermark() {
        watermarkConfig.setCenterLocationType(CenterLocationTypeEnum.LEFT_CENTER);
        byte[] executor = easyWatermark.text("今天天气真不错")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testRightCenterSingleWatermark() {
        watermarkConfig.setCenterLocationType(CenterLocationTypeEnum.RIGHT_CENTER);
        byte[] executor = easyWatermark.text("今天天气真不错")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testVerticalCenterMultiWatermark() {
        watermarkConfig.setCenterLocationType(CenterLocationTypeEnum.VERTICAL_CENTER);
        byte[] executor = easyWatermark.text("a今天天气真不错a", "aabb明天天气也不错aabb", "aabbcc后天天气也不错aabbcc")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testTopCenterMultiWatermark() {
        watermarkConfig.setCenterLocationType(CenterLocationTypeEnum.TOP_CENTER);
        byte[] executor = easyWatermark.text("a今天天气真不错a", "aabb明天天气也不错aabb", "aabbcc后天天气也不错aabbcc")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testBottomCenterMultiWatermark() {
        watermarkConfig.setCenterLocationType(CenterLocationTypeEnum.BOTTOM_CENTER);
        byte[] executor = easyWatermark.text("a今天天气真不错a", "aabb明天天气也不错aabb", "aabbcc后天天气也不错aabbcc")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testLeftCenterMultiWatermark() {
        watermarkConfig.setCenterLocationType(CenterLocationTypeEnum.LEFT_CENTER);
        byte[] executor = easyWatermark.text("a今天天气真不错a", "aabb明天天气也不错aabb", "aabbcc后天天气也不错aabbcc")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testRightCenterMultiWatermark() {
        watermarkConfig.setCenterLocationType(CenterLocationTypeEnum.RIGHT_CENTER);
        byte[] executor = easyWatermark.text("a今天天气真不错a", "aabb明天天气也不错aabb", "aabbcc后天天气也不错aabbcc")
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testVerticalCenterImageWatermark() {
        watermarkConfig.setCenterLocationType(CenterLocationTypeEnum.VERTICAL_CENTER);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testTopCenterImageWatermark() {
        watermarkConfig.setCenterLocationType(CenterLocationTypeEnum.TOP_CENTER);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testBottomCenterImageWatermark() {
        watermarkConfig.setCenterLocationType(CenterLocationTypeEnum.BOTTOM_CENTER);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testLeftCenterImageWatermark() {
        watermarkConfig.setCenterLocationType(CenterLocationTypeEnum.LEFT_CENTER);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testRightCenterImageWatermark() {
        watermarkConfig.setCenterLocationType(CenterLocationTypeEnum.RIGHT_CENTER);
        byte[] executor = easyWatermark.image(getByte("100-50-blue.png"))
                .executor();
        saveOutPutFile(executor, dir, type);
    }
}
