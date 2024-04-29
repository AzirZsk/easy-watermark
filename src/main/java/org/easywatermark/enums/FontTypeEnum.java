package org.easywatermark.enums;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author zhangshukun
 * @since 2024/04/17
 */
public enum FontTypeEnum {

    TRUE_TYPE(Font.TRUETYPE_FONT),

    TYPE1(Font.TYPE1_FONT);

    private final int value;

    FontTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FontTypeEnum getFontType(File fontFile) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            return TRUE_TYPE;
        } catch (FontFormatException | IOException e) {
            return TYPE1;
        }
    }
}
