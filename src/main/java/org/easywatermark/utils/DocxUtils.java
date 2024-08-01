package org.easywatermark.utils;

import org.docx4j.dml.wordprocessingDrawing.*;
import org.docx4j.vml.*;
import org.docx4j.vml.officedrawing.CTLock;
import org.docx4j.vml.wordprocessingDrawing.CTWrap;
import org.docx4j.wml.Pict;
import org.docx4j.wml.R;
import org.easywatermark.core.constant.StringConstant;

import javax.xml.bind.JAXBElement;
import java.awt.*;
import java.util.List;

/**
 * @author zhangshukun
 * @since 2024/07/02
 */
public class DocxUtils {

    private static final String SHAPE_SPID = "_x0000_s2049";

    private static final String SHAPE_TYPE = "#_x0000_t136";

    private static final String TEXT_PATH_FONT_NAME = "v-text-align:left;font-family:\"%s\";";

    public static CTTextPath createNormalTextPath(String watermark, String fontName) {
        CTTextPath res = new CTTextPath();
        res.setStyle(String.format(TEXT_PATH_FONT_NAME, fontName));
        res.setString(watermark);
        return res;
    }

    /**
     * create Shape object
     *
     * @param color shape color
     * @return shape object
     */
    public static CTShape createNormalShape(Color color) {
        CTShape res = new CTShape();
        res.setFillcolor(EasyWatermarkUtils.hexColor(color));
        res.setVmlId(StringConstant.EASY_WATERMARK_FRAMEWORK);
        res.setStroked(org.docx4j.vml.STTrueFalse.F);
        res.setSpid(SHAPE_SPID);
        res.setType(SHAPE_TYPE);
        return res;
    }

    /**
     * Creates a docx R object
     *
     * @param textPath textPath
     * @param ctShape  ctShape
     * @param alpha    The transparency value for the watermark.
     * @return R object
     */
    public static R createR(CTTextPath textPath, CTShape ctShape, float alpha) {
        CTFill ctFill = new CTFill();
        ctFill.setOn(org.docx4j.vml.STTrueFalse.T);
        ctFill.setOpacity(String.valueOf(alpha));
        JAXBElement<CTFill> fill = DocxUtils.createJAXBElement(ctFill);

        // disable stroke
        CTStroke ctStroke = new CTStroke();
        ctStroke.setOn(org.docx4j.vml.STTrueFalse.F);
        JAXBElement<CTStroke> stroke = DocxUtils.createJAXBElement(ctStroke);

        // disable operate
        CTLock ctLock = new CTLock();
        ctLock.setExt(STExt.VIEW);
        ctLock.setAspectratio(org.docx4j.vml.officedrawing.STTrueFalse.T);
        JAXBElement<CTLock> lock = DocxUtils.createJAXBElement(ctLock);

        // watermark text
        JAXBElement<CTTextPath> textpath = DocxUtils.createJAXBElement(textPath);

        // watermark shape
        List<JAXBElement<?>> egShapeElements = ctShape.getEGShapeElements();
        egShapeElements.add(fill);
        egShapeElements.add(stroke);
        egShapeElements.add(lock);
        egShapeElements.add(textpath);

        JAXBElement<CTShape> shape = DocxUtils.createJAXBElement(ctShape);
        Pict pict = new Pict();
        pict.getAnyAndAny().add(shape);

        R r = new R();
        JAXBElement<Pict> rPict = DocxUtils.createJAXBElement(pict);
        r.getContent().add(rPict);
        return r;
    }

    /**
     * copy properties by inline
     *
     * @param imageInline inline image
     * @param x           x, unit is pt
     * @param y           y, unit is pt
     * @return anchor image
     */
    public static Anchor createAnchorByInline(Inline imageInline, float x, float y) {
        Anchor anchor = new Anchor();
        CTPosH ctPosH = new CTPosH();
        ctPosH.setRelativeFrom(STRelFromH.MARGIN);
        ctPosH.setPosOffset((int) x * 12700);

        CTPosV ctPosV = new CTPosV();
        ctPosV.setRelativeFrom(STRelFromV.MARGIN);
        ctPosV.setPosOffset((int) y * 12700);

        anchor.setPositionH(ctPosH);
        anchor.setPositionV(ctPosV);

        anchor.setExtent(imageInline.getExtent());
        anchor.setEffectExtent(imageInline.getEffectExtent());
        anchor.setDocPr(imageInline.getDocPr());
        anchor.setCNvGraphicFramePr(imageInline.getCNvGraphicFramePr());
        anchor.setGraphic(imageInline.getGraphic());
        return anchor;
    }

    /**
     * create JAXBElement
     *
     * @param value need package JAXBElement object
     * @param <T>
     * @return after package JAXBElement
     */
    @SuppressWarnings("all")
    public static <T> JAXBElement<T> createJAXBElement(T value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();
        org.docx4j.vml.ObjectFactory vmlObjectFactory = new org.docx4j.vml.ObjectFactory();
        org.docx4j.vml.wordprocessingDrawing.ObjectFactory wpdObjectFactory = new org.docx4j.vml.wordprocessingDrawing.ObjectFactory();
        org.docx4j.vml.officedrawing.ObjectFactory odObjectFactory = new org.docx4j.vml.officedrawing.ObjectFactory();
        Class<?> clazz = value.getClass();
        if (clazz == Pict.class) {
            return (JAXBElement<T>) wmlObjectFactory.createRPict(((Pict) value));
        }
        if (clazz == CTShape.class) {
            return (JAXBElement<T>) vmlObjectFactory.createShape((CTShape) value);
        }
        if (clazz == CTWrap.class) {
            return (JAXBElement<T>) wpdObjectFactory.createWrap((CTWrap) value);
        }
        if (clazz == CTFill.class) {
            return (JAXBElement<T>) vmlObjectFactory.createFill((CTFill) value);
        }
        if (clazz == CTStroke.class) {
            return (JAXBElement<T>) vmlObjectFactory.createStroke((CTStroke) value);
        }
        if (clazz == CTLock.class) {
            return (JAXBElement<T>) odObjectFactory.createLock((CTLock) value);
        }
        if (clazz == CTTextPath.class) {
            return (JAXBElement<T>) vmlObjectFactory.createTextpath((CTTextPath) value);
        }
        // other type.....
        throw new UnsupportedOperationException("Unsupported value type: " + clazz.getName());
    }
}
