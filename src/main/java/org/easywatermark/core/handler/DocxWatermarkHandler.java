package org.easywatermark.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.vml.*;
import org.docx4j.vml.officedrawing.CTLock;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.*;
import org.easywatermark.core.AbstractWatermarkHandler;
import org.easywatermark.core.EasyWatermarkCustomDraw;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.core.constant.DocxConstant;
import org.easywatermark.entity.Point;
import org.easywatermark.enums.EasyWatermarkTypeEnum;
import org.easywatermark.enums.WatermarkTypeEnum;
import org.easywatermark.exception.DocxWatermarkHandlerException;
import org.easywatermark.exception.LoadFontException;
import org.easywatermark.utils.DocxUtils;
import org.easywatermark.utils.EasyWatermarkUtils;

import javax.xml.bind.JAXBElement;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.easywatermark.core.constant.DocxConstant.TWIP_TO_POINT;

/**
 * Office docx watermark handler.
 * The origin is in the upper left corner
 *
 * @author zhangshukun
 * @since 2024/07/02
 */
@Slf4j
public class DocxWatermarkHandler extends AbstractWatermarkHandler<Font, Object> {

    private static final String SHAPE_SPID = "_x0000_s2049";

    private static final String SHAPE_TYPE = "#_x0000_t136";

    private static final String WARP = "\\n";

    private static final ObjectFactory FACTORY = Context.getWmlObjectFactory();

    private StringBuilder watermarkShapeStyle;

    private int headerXmlCount = 0;

    private WordprocessingMLPackage file;

    private FontMetrics fontMetrics;

    private float ascent;

    private float descent;

    /**
     * docx file section wrapper.
     * include:
     * 1. header info
     * 2. footer info
     * 3. page info
     */
    private List<SectionWrapper> sectionWrapperList;

    public DocxWatermarkHandler(byte[] data, FontConfig fontConfig, WatermarkConfig watermarkConfig) {
        super(data, fontConfig, watermarkConfig);
    }

    @Override
    protected void initGraphics() {
    }

    @Override
    protected void initFont() {
        try {
            Font font;
            if (fontConfig.getFontFile() != null) {
                font = Font.createFont(Font.TRUETYPE_FONT, fontConfig.getFontFile());
            } else {
                font = new Font(fontConfig.getFontName(), fontConfig.getFontStyle(), fontConfig.getFontSize());
            }
            this.font = font.deriveFont(fontConfig.getFontStyle(), (float) fontConfig.getFontSize());
        } catch (FontFormatException | IOException e) {
            log.error("Load font error. Font file:{}", fontConfig.getFontFile(), e);
            throw new LoadFontException("Load font error.", e);
        }
    }

    @Override
    protected void initEnvironment() {
        this.sectionWrapperList = file.getDocumentModel().getSections();
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        this.fontMetrics = graphics.getFontMetrics(this.font);
        this.ascent = fontMetrics.getAscent();
        this.descent = fontMetrics.getDescent();
    }

    @Override
    protected float getFileWidth(int page) {
        checkPageInfoList();
        return sectionWrapperList.get(page).getPageDimensions().getPgSz().getW().floatValue() / TWIP_TO_POINT;
    }

    @Override
    protected float getFileHeight(int page) {
        checkPageInfoList();
        return sectionWrapperList.get(page).getPageDimensions().getPgSz().getH().floatValue() / TWIP_TO_POINT;
    }

    @Override
    protected float getWatermarkImageWidth() {
        return 0;
    }

    @Override
    protected float getWatermarkImageHeight() {
        return 0;
    }

    @Override
    protected byte[] export(EasyWatermarkTypeEnum watermarkType) {
        ByteArrayOutputStream res = new ByteArrayOutputStream();
        try {
            file.save(res);
        } catch (Docx4JException e) {
            log.error("Export data error.", e);
            throw new DocxWatermarkHandlerException("Export data error.", e);
        }
        return res.toByteArray();
    }

    private void addWatermark(CTTextPath textPath, CTShape shape, Integer pageNumber) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Page:{},Shape style:'{}'", pageNumber, shape.getStyle());
            }
            Hdr hdr = createWatermarkHdr(textPath, shape);
            Relationship watermarkRelationship = createWatermarkRelationship(file, hdr);
            SectionWrapper sectionWrapper = sectionWrapperList.get(pageNumber);
            SectPr sectPr = sectionWrapper.getSectPr();
            if (sectPr == null) {
                sectPr = FACTORY.createSectPr();
                file.getMainDocumentPart().addObject(sectPr);
                sectionWrapper.setSectPr(sectPr);
            }

            HeaderReference headerReference = FACTORY.createHeaderReference();
            headerReference.setId(watermarkRelationship.getId());
            headerReference.setType(HdrFtrRef.DEFAULT);
            sectPr.getEGHdrFtrReferences().add(headerReference);
        } catch (InvalidFormatException e) {
            log.error("");
            throw new DocxWatermarkHandlerException("Create header part error.", e);
        }
    }

    @Override
    public void customDraw(EasyWatermarkCustomDraw easyWatermarkCustomDraw) {

    }

    @Override
    public void drawCenterWatermark() {
        WatermarkTypeEnum watermarkType = getWatermarkType();
        switch (watermarkType) {
            case SINGLE_TEXT:
                for (int i = 0; i < sectionWrapperList.size(); i++) {
                    Point point = calcCenterWatermarkPoint(watermarkText, i);
                    drawString(point.getX(), point.getY(), watermarkText, i);
                }
                break;
            case MULTI_TEXT:
                break;
            case IMAGE:

                break;
            default:
                throw new UnsupportedOperationException("Not support watermark type.");
        }
    }

    @Override
    public void drawOverspreadWatermark() {
        throw new UnsupportedOperationException("Not support docx type overspread watermark.");
    }

    @Override
    public void drawDiagonalWatermark() {

    }

    @Override
    public void loadFile(byte[] data) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        try {
            this.file = WordprocessingMLPackage.load(byteArrayInputStream);
        } catch (Docx4JException e) {
            log.error("Load data error.", e);
            throw new DocxWatermarkHandlerException("Load data error.", e);
        }
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public float getStringWidth(String text) {
        return fontMetrics.stringWidth(text);
    }

    @Override
    public float getStringHeight() {
        return fontMetrics.getHeight();
    }

    @Override
    public void drawString(float x, float y, String text, int pageNumber) {
        CTShape normalShape = createNormalShape();
        initWatermarkShapeStyle();
        SectionWrapper sectionWrapper = sectionWrapperList.get(pageNumber);
        SectPr.PgMar pgMar = sectionWrapper.getSectPr().getPgMar();
        watermarkShapeStyle.append(setLocation(pgMar.getHeader().floatValue(), pgMar.getLeft().floatValue(), x, y));
        watermarkShapeStyle.append(String.format(DocxConstant.WATERMARK_SIZE, getStringWidth(text), getStringHeight()));
        normalShape.setStyle(watermarkShapeStyle.toString());
        addWatermark(createNormalTextPath(text), normalShape, pageNumber);
    }

    @Override
    public void drawMultiLineString(float x, float y, List<String> text, int pageNumber) {
        String watermarkText = String.join(WARP, text);
        drawString(x, y, watermarkText, pageNumber);
    }

    @Override
    public void drawImage(float x, float y, byte[] data, int pageNumber) {

    }

    @Override
    public void rotate(float angle, float x, float y, int pageNumber) {

    }

    /**
     * create watermark header part
     *
     * @param wordprocessingMLPackage file
     * @param hdr                     header xml
     * @return docx relationship
     */
    private Relationship createWatermarkRelationship(WordprocessingMLPackage wordprocessingMLPackage, Hdr hdr) throws InvalidFormatException {
        HeaderPart headerPart = new HeaderPart(new PartName("/word/easy-watermark-header" + headerXmlCount++ + ".xml"));
        headerPart.setJaxbElement(hdr);
        return wordprocessingMLPackage.getMainDocumentPart().addTargetPart(headerPart);
    }

    /**
     * create watermark xml
     *
     * @param textPath watermark text
     * @param ctShape  watermark shape
     * @return watermark xml
     */
    private Hdr createWatermarkHdr(CTTextPath textPath, CTShape ctShape) {
        Hdr res = FACTORY.createHdr();
        P watermarkHeader = createDocxHeader(textPath, ctShape);
        res.getContent().add(watermarkHeader);
        return res;
    }

    private void checkPageInfoList() {
        if (this.sectionWrapperList == null || this.sectionWrapperList.isEmpty()) {
            throw new DocxWatermarkHandlerException("Page info list is empty.");
        }
    }

    private P createDocxHeader(CTTextPath textPath, CTShape ctShape) {
        CTFill ctFill = new CTFill();
        ctFill.setOn(org.docx4j.vml.STTrueFalse.T);
        ctFill.setOpacity(String.valueOf(watermarkConfig.getAlpha()));
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

        P p = new P();
        p.getContent().add(r);
        return p;
    }

    private CTTextPath createNormalTextPath(String watermark) {
        CTTextPath res = new CTTextPath();
        res.setStyle(String.format("font-family:\"%s\";", font.getFontName()));
        res.setString(watermark);
        return res;
    }

    private CTShape createNormalShape() {
        CTShape res = new CTShape();
        res.setFillcolor(EasyWatermarkUtils.hexColor(watermarkConfig.getColor()));
        res.setVmlId("easy-watermark framework");
        res.setStroked(org.docx4j.vml.STTrueFalse.F);
        res.setSpid(SHAPE_SPID);
        res.setType(SHAPE_TYPE);
        return res;
    }

    /**
     * Init watermark shape style
     */
    private void initWatermarkShapeStyle() {
        watermarkShapeStyle = new StringBuilder(DocxConstant.DEFAULT_POSITION_TYPE);
    }

    /**
     * Use 'margin-left:-70pt;margin-top:-30pt;'
     *
     * @param headerTopMargin  The distance between the header top of the docx document and the top
     * @param headerLeftMargin The distance between the header left of the docx document and the top
     * @param x                draw x
     * @param y                draw y
     * @return watermark location:'margin-left:-70pt;margin-top:-30pt;'
     */
    private String setLocation(float headerTopMargin, float headerLeftMargin, float x, float y) {
        float leftMargin = -headerLeftMargin / TWIP_TO_POINT;
        float topMargin = -headerTopMargin / TWIP_TO_POINT;

        leftMargin += x;
        topMargin += y;
        return String.format(DocxConstant.WATERMARK_LOCATION, leftMargin, topMargin);
    }
}