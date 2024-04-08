package org.azir.easywatermark;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.encoding.StandardEncoding;
import org.azir.easywatermark.core.EasyWatermark;
import org.azir.easywatermark.core.calculate.impl.PageCenteringCalculator;
import org.azir.easywatermark.core.font.FontMetrics;
import org.azir.easywatermark.core.calculate.AbstractCalculate;
import org.azir.easywatermark.entity.Point;
import org.azir.easywatermark.entity.WatermarkParam;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author Azir
 * @date 2022/11/13
 */
@Slf4j
public class ImageWatermarkTest {

    @Test
    public void run() {
        byte[] asdfasdfs = EasyWatermark.create()
                .text("asdfasdf")
                // 获取resources的test.png
                .file(new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("test.png")).getFile()))
                .executor();

    }
}
