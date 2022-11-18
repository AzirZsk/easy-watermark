package org.azir.allwatermark;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.encoding.StandardEncoding;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author Azir
 * @date 2022/11/13
 */
public class ImageWatermarkTest {

    public static void main(String[] args) throws IOException, FontFormatException, NoSuchFieldException, IllegalAccessException {
        BufferedImage image = ImageIO.read(new File("C:\\Users\\Administrator\\Pictures\\Camera Roll\\WIN_20200317_20_03_20_Pro.jpg"));
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.BLACK);
        Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(ImageWatermarkTest.class.getResourceAsStream("/STZHONGS.TTF")));
        Field declaredField = Font.class.getDeclaredField("pointSize");
        declaredField.setAccessible(true);
        declaredField.setInt(font, 50);

        graphics.setFont(font);
        graphics.drawString("阿斯蒂芬阿斯蒂芬阿斯蒂芬", 10, 100);
        graphics.dispose();
        File file = new File("D:\\work\\all-watermark\\target\\2.jpg");
        ImageIO.write(image, "jpg", new FileOutputStream(file));
    }

    @Test
    public void test() throws IOException {
        PDDocument document = PDDocument.load(getClass().getResourceAsStream("/test.pdf"));
        PDType0Font font = PDType0Font.load(document, getClass().getResourceAsStream("/STZHONGS.TTF"));
        PDTrueTypeFont load = PDTrueTypeFont.load(document, getClass().getResourceAsStream("/simsun.ttc"), StandardEncoding.INSTANCE);

        System.out.println();
    }
}
