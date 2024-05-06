package org.easywatermark.core;

import org.easywatermark.core.provider.FontProvider;
import org.easywatermark.core.provider.GraphicsProvider;
import org.easywatermark.entity.PageInfo;

/**
 * @author zhangshukun
 * @since 2024/04/11
 */
public interface EasyWatermarkCustomDraw {

    /**
     * Draw custom content.
     * <p>
     * This method will be called once or multiple times, and the specific call will vary depending on the file type
     *
     * @param pageInfo         page info
     * @param graphicsProvider graphics provider
     * @param fontProvider     font provider
     */
    void draw(PageInfo pageInfo, GraphicsProvider graphicsProvider, FontProvider fontProvider);
}
