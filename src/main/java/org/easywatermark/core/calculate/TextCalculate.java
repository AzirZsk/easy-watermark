package org.easywatermark.core.calculate;

import java.util.Collections;
import java.util.List;

/**
 * @author zhangshukun
 * @date 2023/02/23
 */
public interface TextCalculate {

    default List<String> calculateText() {
        return Collections.emptyList();
    }
}
