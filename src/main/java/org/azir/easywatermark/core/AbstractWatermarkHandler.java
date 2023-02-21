package org.azir.easywatermark.core;

import lombok.extern.slf4j.Slf4j;
import org.azir.easywatermark.entity.WatermarkParam;
import org.azir.easywatermark.utils.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author zhangshukun
 * @date 2022/11/8
 */
@Slf4j
public abstract class AbstractWatermarkHandler<F> implements WatermarkHandler, FontType<F> {

}
