package org.easywatermark.core.config;

import lombok.Data;

import java.awt.*;
import java.io.File;

/**
 * @author zhangshukun
 * @since 2024/04/08
 */
@Data
public class FontConfig {

    private File fontFile;

    /**
     * Default font name is Dialog
     */
    private String fontName = "Dialog";

    private int fontSize = 12;

    /**
     * @see Font#PLAIN
     * @see Font#BOLD
     * @see Font#ITALIC
     */
    private int fontStyle = Font.PLAIN;
}
