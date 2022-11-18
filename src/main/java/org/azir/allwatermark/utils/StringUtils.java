package org.azir.allwatermark.utils;

/**
 * @author zhangshukun
 * @date 2022/11/18
 */
public class StringUtils {

    public static boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }

    public static boolean isNoEmpty(String text) {
        return !isEmpty(text);
    }
}
