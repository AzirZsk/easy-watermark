package org.azir.easywatermark.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
