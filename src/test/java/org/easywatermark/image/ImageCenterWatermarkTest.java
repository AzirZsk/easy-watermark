package org.easywatermark.image;

import lombok.extern.slf4j.Slf4j;
import org.easywatermark.AbstractCenterWatermarkTest;
import org.easywatermark.core.EasyWatermark;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.enums.EasyWatermarkTypeEnum;

/**
 * @author Azir
 * @date 2022/11/13
 */
@Slf4j
public class ImageCenterWatermarkTest extends AbstractCenterWatermarkTest {

    static {
        type = "jpeg";
        dir = "image/center";

        fontConfig = new FontConfig();

        watermarkConfig = new WatermarkConfig();
        watermarkConfig.setAlpha(0.5f);

        easyWatermark = EasyWatermark.create()
                .file(getFile("600-400.png"))
                .config(fontConfig)
                .config(watermarkConfig)
                .easyWatermarkType(EasyWatermarkTypeEnum.CENTER);
    }

}
