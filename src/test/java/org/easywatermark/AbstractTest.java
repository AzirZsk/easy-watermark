package org.easywatermark;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * @author zhangshukun
 * @date 2024/4/17
 */
@Slf4j
public abstract class AbstractTest {

    protected static final String OUT_PUT_DIR = System.getProperty("user.home") + "/Desktop/easywatermark/";

    protected static byte[] getByte(String fileName) {
        try (FileInputStream fileInputStream = new FileInputStream(getFile(fileName))) {
            byte[] watermarkImage = new byte[fileInputStream.available()];
            fileInputStream.read(watermarkImage);
            return watermarkImage;
        } catch (IOException e) {
            log.error("getByte error", e);
            throw new RuntimeException(e);
        }
    }

    protected static File getFile(String fileName) {
        return new File(Objects.requireNonNull(AbstractTest.class.getClassLoader().getResource(fileName)).getFile());
    }

    protected static void saveOutPutFile(byte[] executor, String fileDir, String suffix) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(getOutPutFileName(fileDir, suffix));
            fileOutputStream.write(executor);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getOutPutFileName(String fileDir, String suffix) {
        Exception exception = new Exception();
        StackTraceElement[] stackTrace = exception.getStackTrace();
        String pathName = OUT_PUT_DIR + File.separator + fileDir + File.separator;
        File file = new File(pathName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return pathName + stackTrace[2].getMethodName() + "." + suffix;
    }
}
