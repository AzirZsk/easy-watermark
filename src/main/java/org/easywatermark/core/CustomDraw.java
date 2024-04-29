package org.easywatermark.core;

import org.easywatermark.core.graphics.GraphicsProvider;

/**
 * @author zhangshukun
 * @since 2024/04/11
 */
@FunctionalInterface
public interface CustomDraw {

    /**
     * Draw.
     *
     * @param f                font image like {@link  java.awt.Font}
     * @param g                graphics image like {@link  java.awt.Graphics}
     * @param imageWidth       image width
     * @param imageHeight      image height
     * @param graphicsProvider graphics provider
     * @param <F>              font
     * @param <G>              graphics
     */
    <F, G> void draw(F f, G g, float imageWidth, float imageHeight, GraphicsProvider graphicsProvider);
}
