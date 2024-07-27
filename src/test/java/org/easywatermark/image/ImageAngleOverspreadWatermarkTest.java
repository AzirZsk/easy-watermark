package org.easywatermark.image;

import org.easywatermark.AbstractAngleOverspreadWatermarkTest;
import org.easywatermark.core.EasyWatermark;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.enums.EasyWatermarkTypeEnum;

/**
 * @author zhangshukun
 * @date 2024/4/28
 */
public class ImageAngleOverspreadWatermarkTest extends AbstractAngleOverspreadWatermarkTest {

    static {
        type = "jpeg";
        dir = "image/overspread/angle";

        fontConfig = new FontConfig();

        watermarkConfig = new WatermarkConfig();
        watermarkConfig.setAlpha(0.5f);

        easyWatermark = EasyWatermark.create()
                .file(getFile("600-400.png"))
                .config(fontConfig)
                .config(watermarkConfig)
                .easyWatermarkType(EasyWatermarkTypeEnum.OVERSPREAD);
    }

}
