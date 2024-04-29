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
}
