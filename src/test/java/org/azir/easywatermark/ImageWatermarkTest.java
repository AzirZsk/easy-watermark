package org.azir.easywatermark;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.core.CustomDraw;
import org.azir.easywatermark.core.EasyWatermark;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.core.graphics.GraphicsProvider;
import org.azir.easywatermark.enums.CenterLocationTypeEnum;
import org.azir.easywatermark.enums.DiagonalDirectionTypeEnum;
import org.azir.easywatermark.enums.EasyWatermarkTypeEnum;
import org.azir.easywatermark.enums.OverspreadTypeEnum;
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
                .text("今天天气真好", "明天天气也不错", "后天天气也不错", "大后天天气也不错")
                .file(new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("600-400.png")).getFile()))
                .config(new FontConfig() {
                    {
                        setFontName("宋体");
                        setFontSize(40);
                        setFontStyle(Font.PLAIN);
                    }
                })
                .easyWatermarkType(EasyWatermarkTypeEnum.CENTER)
                .config(new WatermarkConfig() {
                    {
                        setOverspreadType(OverspreadTypeEnum.HIGH);
                        setCenterLocationType(CenterLocationTypeEnum.VERTICAL_CENTER);
                        setDiagonalDirectionType(DiagonalDirectionTypeEnum.BOTTOM_TO_TOP);
                        setAlpha(0.4f);
                    }
                })
                .customDraw(new CustomDraw() {
                    @Override
                    public <F, G> void draw(F f, G g, int imageWidth, int imageHeight, GraphicsProvider graphicsProvider) {
                        graphicsProvider.drawString(0, 0, "自定义绘制");
                    }
                })
                .executor();
        // 输出到桌面
        FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.home") + "/Desktop/test.jpeg");
        fileOutputStream.write(executor);
        fileOutputStream.close();
    }
}
