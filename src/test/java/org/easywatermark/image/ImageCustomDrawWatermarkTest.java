package org.easywatermark.image;

import lombok.extern.slf4j.Slf4j;
import org.easywatermark.AbstractCustomDrawWatermarkTest;
import org.easywatermark.core.EasyWatermark;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.enums.EasyWatermarkTypeEnum;

/**
 * @author zhangshukun
 * @date 2024/5/1
 */
@Slf4j
public class ImageCustomDrawWatermarkTest extends AbstractCustomDrawWatermarkTest {

    static {
        type = "jpeg";
        dir = "image/custom";

        fontConfig = new FontConfig();

        watermarkConfig = new WatermarkConfig();
        watermarkConfig.setAlpha(0.5f);

        easyWatermark = EasyWatermark.create()
                .file(getFile("600-400.png"))
                .config(fontConfig)
                .config(watermarkConfig)
                .easyWatermarkType(EasyWatermarkTypeEnum.CUSTOM);
    }

}
