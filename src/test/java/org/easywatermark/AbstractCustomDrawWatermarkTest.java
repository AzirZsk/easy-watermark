package org.easywatermark;

import org.junit.Test;

/**
 * @author zhangshukun
 * @date 2024/7/27
 */
public abstract class AbstractCustomDrawWatermarkTest extends AbstractTest {

    public void testDrawSingleWatermarkText() {
        byte[] executor = easyWatermark.customDraw((pageInfo, graphicsProvider, fontProvider) -> {
            int pageNo = pageInfo.getPageNo();
            graphicsProvider.drawString(300, 20, "asdfasdfasdfzxdvzxcv", pageNo);
        }).executor();
        saveOutPutFile(executor, dir, type);
    }

    @Test
    public void testOddPageWatermarkTest() {
        byte[] executor = easyWatermark.customDraw((pageInfo, graphicsProvider, fontProvider) -> {
            int pageNo = pageInfo.getPageNo();
            if (pageNo % 2 == 1) {
                graphicsProvider.drawString(300, 20, "asdfasdfasdfzxdvzxcv", pageNo);
            }
        }).executor();
        saveOutPutFile(executor, dir, type);
    }
}
