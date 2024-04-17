package org.azir.easywatermark;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * @author zhangshukun
 * @date 2024/4/17
 */
@Slf4j
public abstract class AbstractTest {

    protected static final String OUT_PUT_DIR = System.getProperty("user.home") + "/Desktop/easywatermark/";

    protected byte[] getByte(String fileName) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(getFile(fileName))) {
            byte[] watermarkImage = new byte[fileInputStream.available()];
            fileInputStream.read(watermarkImage);
            return watermarkImage;
        } catch (Exception e) {
            log.error("getByte error", e);
            throw e;
        }
    }

    protected File getFile(String fileName) {
        return new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName)).getFile());
    }

    protected String getOutPutFileName(String suffix) {
        Exception exception = new Exception();
        StackTraceElement[] stackTrace = exception.getStackTrace();
        File file = new File(OUT_PUT_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        return OUT_PUT_DIR + stackTrace[1].getMethodName() + "." + suffix;
    }
}
