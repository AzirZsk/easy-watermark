package org.easywatermark.core;

import org.easywatermark.core.font.FontProvider;
import org.easywatermark.core.graphics.GraphicsProvider;
import org.easywatermark.entity.PageInfo;

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
     * @param graphicsProvider graphics provider
     * @param <F>              font
     * @param <G>              graphics
     */
    <F, G> void draw(PageInfo pageInfo, GraphicsProvider graphicsProvider, FontProvider fontProvider);
}
