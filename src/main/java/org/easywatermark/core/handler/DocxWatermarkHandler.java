package org.easywatermark.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
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
import org.easywatermark.entity.PageInfo;
import org.easywatermark.enums.EasyWatermarkTypeEnum;
import org.easywatermark.exception.DocxWatermarkHandlerException;
import org.easywatermark.exception.LoadFontException;
import org.easywatermark.utils.DocxUtils;
import org.easywatermark.utils.EasyWatermarkUtils;
import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.bind.JAXBElement;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangshukun
 * @since 2024/07/02
 */
@Slf4j

public class DocxWatermarkHandler extends AbstractWatermarkHandler<Font, Object> {

    private static final String TAG_NAME_PG_SZ = "w:pgSz";

    private static final String SHAPE_SPID = "_x0000_s2049";

    private static final String SHAPE_TYPE = "#_x0000_t136";

    private static final ObjectFactory FACTORY = Context.getWmlObjectFactory();

    private WordprocessingMLPackage file;

    /**
     * docx file xml format.
     */
    private Document document;

    private List<PageInfo> pageInfoList;

    private FontMetrics fontMetrics;

    private CTTextPath textWatermarkControl;

    private CTShape ctShape;

    public DocxWatermarkHandler(byte[] data, FontConfig fontConfig, WatermarkConfig watermarkConfig) {
        super(data, fontConfig, watermarkConfig);
    }

    @Override
    protected void initGraphics() {
        this.textWatermarkControl = new CTTextPath();
        this.ctShape = new CTShape();
        ctShape.setFillcolor(EasyWatermarkUtils.hexColor(watermarkConfig.getColor()));
        ctShape.setVmlId(RandomStringUtils.random(10));
        ctShape.setStroked(org.docx4j.vml.STTrueFalse.F);
        ctShape.setSpid(SHAPE_SPID);
        ctShape.setType(SHAPE_TYPE);
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
            BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = image.getGraphics();
            this.fontMetrics = graphics.getFontMetrics(font);
        } catch (FontFormatException | IOException e) {
            log.error("Load font error. Font file:{}", fontConfig.getFontFile(), e);
            throw new LoadFontException("Load font error.", e);
        }
    }

    @Override
    protected void initEnvironment() {
        this.document = XmlUtils.marshaltoW3CDomDocument(this.file.getMainDocumentPart().getJaxbElement());
        NodeList pgSzNodeList = document.getElementsByTagName(TAG_NAME_PG_SZ);
        List<PageInfo> pageInfoList = new ArrayList<>();
        for (int i = 0; i < pgSzNodeList.getLength(); i++) {
            Node item = pgSzNodeList.item(i);
            NamedNodeMap attributes = item.getAttributes();
            Node heightNode = attributes.getNamedItem("w:h");
            Node widthNode = attributes.getNamedItem("w:w");
            PageInfo pageInfo = new PageInfo(Float.parseFloat(widthNode.getTextContent()), Float.parseFloat(heightNode.getTextContent()), i);
            pageInfoList.add(pageInfo);
        }
        this.pageInfoList = pageInfoList;
    }

    @Override
    protected float getFileWidth(int page) {
        checkPageInfoList();
        return pageInfoList.get(page).getWidth();
    }

    @Override
    protected float getFileHeight(int page) {
        checkPageInfoList();
        return pageInfoList.get(page).getHeight();
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
        addWatermarkToFile();
        ByteArrayOutputStream res = new ByteArrayOutputStream();
        try {
            file.save(res);
        } catch (Docx4JException e) {
            log.error("Export data error.", e);
            throw new DocxWatermarkHandlerException("Export data error.", e);
        }
        return res.toByteArray();
    }

    private void addWatermarkToFile() {
        try {
            Relationship watermarkRelationship = createWatermarkHeaderPart(file);

            List<SectionWrapper> sections = file.getDocumentModel().getSections();
            SectionWrapper lastSectionWrapper = sections.get(sections.size() - 1);
            SectPr sectPr = lastSectionWrapper.getSectPr();
            if (sectPr == null) {
                sectPr = FACTORY.createSectPr();
                file.getMainDocumentPart().addObject(sectPr);
                lastSectionWrapper.setSectPr(sectPr);
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

    }

    @Override
    public void drawMultiLineString(float x, float y, List<String> text, int pageNumber) {

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
     * @return docx xml relationship
     */
    private Relationship createWatermarkHeaderPart(WordprocessingMLPackage wordprocessingMLPackage) throws InvalidFormatException {
        HeaderPart headerPart = new HeaderPart();
        Hdr hdr = getWatermarkHdr();
        headerPart.setJaxbElement(hdr);
        return wordprocessingMLPackage.getMainDocumentPart().addTargetPart(headerPart);
    }

    private Hdr getWatermarkHdr() {
        Hdr res = FACTORY.createHdr();
        P qq = createDocxHeader();
        res.getContent().add(qq);
        return res;
    }

    private void checkPageInfoList() {
        if (this.pageInfoList == null || this.pageInfoList.isEmpty()) {
            throw new DocxWatermarkHandlerException("Page info list is empty.");
        }
    }

    private P createDocxHeader() {
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
        JAXBElement<CTTextPath> textpath = DocxUtils.createJAXBElement(textWatermarkControl);

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
}
