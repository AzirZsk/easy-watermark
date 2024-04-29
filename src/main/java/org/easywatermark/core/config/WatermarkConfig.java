package org.easywatermark.core.config;

import lombok.Data;
import org.easywatermark.enums.CenterLocationTypeEnum;
import org.easywatermark.enums.DiagonalDirectionTypeEnum;
import org.easywatermark.enums.OverspreadTypeEnum;

import java.awt.*;


/**
 * @author zhangshukun
 * @date 2023/02/23
 */
@Data
public class WatermarkConfig {

    private float locationX;

    private float locationY;

    private Color color = Color.BLACK;

    private boolean ignoreRotation = true;

    private float alpha = 1;

    private double fontSize = 60;

    private OverspreadTypeEnum overspreadType = OverspreadTypeEnum.NORMAL;

    private CenterLocationTypeEnum centerLocationType = CenterLocationTypeEnum.VERTICAL_CENTER;

    private DiagonalDirectionTypeEnum diagonalDirectionType = DiagonalDirectionTypeEnum.TOP_TO_BOTTOM;

    /**
     * An angle, in degrees
     * The angle of clockwise rotation
     */
    private float angle = 0;
}