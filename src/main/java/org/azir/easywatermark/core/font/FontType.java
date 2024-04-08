package org.azir.easywatermark.core.font;

/**
 * @author Azir
 * @date 2022/11/13
 */
public interface FontType {

    /**
     * Load fonts, Current only support TrueTypeFont(.ttf) fonts;
     *
     * @param fontFile font fontFile
     */
    void load(byte[] fontFile);

}
