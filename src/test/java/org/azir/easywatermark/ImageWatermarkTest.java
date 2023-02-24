package org.azir.easywatermark;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.encoding.StandardEncoding;
import org.azir.easywatermark.core.EasyWatermark;
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
public class ImageWatermarkTest {

    @Test
    public void test() throws IOException {
        String text = "hhhhhhhh";
        byte[] hhhhhhhh = EasyWatermark.load(new FileInputStream("D:\\file\\watermark\\qqq.pdf"))
                .watermark(text)
                .font(getClass().getResourceAsStream("/STZHONGS.TTF"))
                .calculate(new AbstractCalculate() {
                    @Override
                    public WatermarkParam calculateLocation(Point topLeftCornerPoint, Point bottomRightCornerPoint,
                                                            FontMetrics fontMetrics, String watermarkText) {
                        double topLeftCornerPointX = topLeftCornerPoint.getX();
                        double topLeftCornerPointY = topLeftCornerPoint.getY();

                        double bottomRightCornerPointX = bottomRightCornerPoint.getX();
                        double bottomRightCornerPointY = bottomRightCornerPoint.getY();

                        WatermarkParam res = new WatermarkParam();
                        res.setY(Math.abs(topLeftCornerPointY + bottomRightCornerPointY) / 2);
                        double stringWidth = fontMetrics.getStringWidth(watermarkText);
                        res.setX(Math.abs(topLeftCornerPointX + bottomRightCornerPointX - stringWidth) / 2);
                        return res;
                    }
                })
                .execute();
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\file\\watermark\\target1.pdf");
        fileOutputStream.write(hhhhhhhh);
        fileOutputStream.close();
    }
}
