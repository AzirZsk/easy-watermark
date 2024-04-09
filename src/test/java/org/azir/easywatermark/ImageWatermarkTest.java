package org.azir.easywatermark;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.encoding.StandardEncoding;
import org.azir.easywatermark.core.EasyWatermark;
import org.azir.easywatermark.core.calculate.impl.PageCenteringCalculator;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.font.FontMetrics;
import org.azir.easywatermark.core.calculate.AbstractCalculate;
import org.azir.easywatermark.entity.Point;
import org.azir.easywatermark.entity.WatermarkParam;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
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
                .text("张淑坤")
                // 获取resources的test.png
                .file(new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("test.png")).getFile()))
                .config(new FontConfig(){
                    {
                        setFontName("宋体");
                        setFontSize(20);
                        setFontStyle(Font.PLAIN);
                    }
                })
                .executor();
        // 输出到桌面
        FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.home") + "/Desktop/test.jpeg");
        fileOutputStream.write(executor);
    }
}
