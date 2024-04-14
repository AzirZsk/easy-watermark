package org.azir.easywatermark.enums;

/**
 * Intensive watermark.
 *
 * @author zhangshukun
 * @date 2022/11/15
 */
public enum OverspreadTypeEnum {

    /**
     *
     */
    LOW(0.33f),

    /**
     *
     */
    NORMAL(0.66f),

    /**
     *
     */
    HIGH(0.99f);

    OverspreadTypeEnum(float coverage) {
        this.coverage = coverage;
    }

    private final float coverage;

    public float getCoverage() {
        return coverage;
    }
}
