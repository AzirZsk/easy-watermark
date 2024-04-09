package org.azir.easywatermark;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.core.EasyWatermark;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.enums.CenterLocationTypeEnum;
import org.azir.easywatermark.enums.WatermarkLocationTypeEnum;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

/**
 * @author Azir
 * @date 2022/11/13
 */
@Slf4j
public class ImageWatermarkTest {

    @SneakyThrows
    @Test
    public void run() {
        byte[] executor = EasyWatermark.create()
                .text("今天天气真好")
                // 获取resources的test.png
                .file(new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("center-test.png")).getFile()))
                .config(new FontConfig() {
                    {
                        setFontName("宋体");
                        setFontSize(20);
                        setFontStyle(Font.PLAIN);
                    }
                })
                .watermarkType(WatermarkLocationTypeEnum.CENTER)
                .executor();
        // 输出到桌面
        FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.home") + "/Desktop/test.jpeg");
        fileOutputStream.write(executor);
        fileOutputStream.close();
    }
}
