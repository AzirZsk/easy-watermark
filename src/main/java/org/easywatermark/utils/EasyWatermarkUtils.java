package org.easywatermark.utils;

/**
 * @author zhangshukun
 * @since 2024/04/09
 */
public class EasyWatermarkUtils {

    public static double calcRadians(double weight, double height) {
        return Math.atan2(height, weight);
    }

    public static double calcDegrees(double weight, double height) {
        return Math.toDegrees(calcRadians(weight, height));
    }

    /**
     * @author zhangshukun
     * @date 2022/11/18
     */
    public static class StringUtils {

        public static boolean isEmpty(String text) {
            return text == null || text.isEmpty();
        }

        public static boolean isNoEmpty(String text) {
            return !isEmpty(text);
        }
    }
}
