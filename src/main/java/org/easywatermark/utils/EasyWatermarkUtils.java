package org.easywatermark.utils;

import java.awt.*;

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

    public static String hexColor(Color color) {
        if (color == null) {
            throw new NullPointerException("color is null");
        }
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}
