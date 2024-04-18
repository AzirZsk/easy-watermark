package org.azir.easywatermark;

import lombok.SneakyThrows;
import org.azir.easywatermark.core.EasyWatermark;
import org.azir.easywatermark.core.config.FontConfig;
import org.junit.Test;

import java.io.FileOutputStream;

/**
 * @author zhangshukun
 * @date 2024/4/17
 */
public class PdfWatermarkTest extends AbstractTest {

    @SneakyThrows
    @Test
    public void testCenterPdfWatermark() {
        byte[] executor = EasyWatermark.create()
                .file(getFile("test-pdf.pdf"))
                .text("今天天气")
                .config(new FontConfig() {{
                    setFontFile(getFile("STZHONGS.TTF"));
                    setFontSize(50);
                }})
                .executor();
        // 输出到桌面
        FileOutputStream fileOutputStream = new FileOutputStream(getOutPutFileName("pdf"));
        fileOutputStream.write(executor);
        fileOutputStream.close();
    }
}
