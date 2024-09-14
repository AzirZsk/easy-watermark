package org.easywatermark.image;

import org.easywatermark.AbstractDiagonalWatermarkTest;
import org.easywatermark.core.EasyWatermark;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.enums.EasyWatermarkTypeEnum;

/**
 * @author zhangshukun
 * @date 2024/4/20
 */
public class ImageDiagonalWatermarkTest extends AbstractDiagonalWatermarkTest {

    static {
        type = "jpeg";
        dir = "image/diagonal";

        fontConfig = new FontConfig();

        watermarkConfig = new WatermarkConfig();
        watermarkConfig.setAlpha(0.5f);

        easyWatermark = EasyWatermark.create()
                .file(getFile("600-400.png"))
                .config(fontConfig)
                .config(watermarkConfig)
                .easyWatermarkType(EasyWatermarkTypeEnum.DIAGONAL);
    }

}
