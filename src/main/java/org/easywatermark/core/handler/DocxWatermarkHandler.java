package org.easywatermark.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.vml.CTShape;
import org.docx4j.vml.CTTextPath;
import org.docx4j.wml.*;
import org.easywatermark.core.AbstractWatermarkHandler;
import org.easywatermark.core.EasyWatermarkCustomDraw;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.core.constant.DocxConstant;
import org.easywatermark.core.constant.StringConstant;
import org.easywatermark.entity.Point;
import org.easywatermark.entity.WatermarkBox;
import org.easywatermark.enums.CenterLocationTypeEnum;
import org.easywatermark.enums.EasyWatermarkTypeEnum;
import org.easywatermark.enums.WatermarkTypeEnum;
import org.easywatermark.exception.DocxWatermarkHandlerException;
import org.easywatermark.exception.LoadFileException;
import org.easywatermark.exception.LoadFontException;
import org.easywatermark.utils.DocxUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.easywatermark.core.constant.DocxConstant.TWIP_TO_POINT;
import static org.easywatermark.core.constant.StringConstant.EASY_WATERMARK_FRAMEWORK;

/**
 * Office docx watermark handler.
 * The origin is in the upper left corner
 *
 * @author zhangshukun
 * @since 2024/07/02
 */
@Slf4j
public class DocxWatermarkHandler extends AbstractWatermarkHandler<Font, Object> {

    private static final ObjectFactory FACTORY = Context.getWmlObjectFactory();

    private final List<HeaderPart> watermarkHeaderPartList = new ArrayList<>();

    private final List<P> watermarkPList = new ArrayList<>();

    private StringBuilder watermarkShapeStyle;

    private WordprocessingMLPackage file;

    private FontMetrics fontMetrics;

    /**
     * docx file section wrapper.
     * include:
     * 1. header info
     * 2. footer info
     * 3. page info
     */
    private List<SectionWrapper> sectionWrapperList;

    private BufferedImage watermarkBufferedImage;

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
            BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = image.getGraphics();
            this.fontMetrics = graphics.getFontMetrics(this.font);
        } catch (FontFormatException | IOException e) {
            log.error("Load font error. Font file:{}", fontConfig.getFontFile(), e);
            throw new LoadFontException("Load font error.", e);
        }
    }

    @Override
    protected void initEnvironment() {
        this.sectionWrapperList = file.getDocumentModel().getSections();
        for (int i = 0; i < sectionWrapperList.size(); i++) {
            P p = new P();
            this.watermarkPList.add(p);

            // init header
            Hdr hdr = FACTORY.createHdr();
            hdr.getContent().add(p);
            // add header file in to docx
            Relationship watermarkRelationship = createEasyWatermarkRelationship(file, hdr, i);
            SectionWrapper sectionWrapper = sectionWrapperList.get(i);
            SectPr sectPr = sectionWrapper.getSectPr();
            if (sectPr == null) {
                sectPr = FACTORY.createSectPr();
                file.getMainDocumentPart().addObject(sectPr);
                sectionWrapper.setSectPr(sectPr);
            }
            // create header reference
            List<CTRel> egHdrFtrReferences = sectPr.getEGHdrFtrReferences();
            HeaderReference headerReference = FACTORY.createHeaderReference();
            headerReference.setId(watermarkRelationship.getId());
            headerReference.setType(HdrFtrRef.DEFAULT);
            egHdrFtrReferences.add(headerReference);
        }

        if (getWatermarkType() == WatermarkTypeEnum.IMAGE) {
            try {
                this.watermarkBufferedImage = ImageIO.read(new ByteArrayInputStream(super.watermarkImage));
            } catch (IOException e) {
                log.warn("Load image error.", e);
                throw new LoadFileException("Load image error.", e);
            }
        }
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
        // ÷ (96f / 72f)  --> px to pt
        return watermarkBufferedImage.getWidth() / (96f / 72f);
    }

    @Override
    protected float getWatermarkImageHeight() {
        // ÷ (96f / 72f)  --> px to pt
        return watermarkBufferedImage.getHeight() / (96f / 72f);
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
                for (int i = 0; i < sectionWrapperList.size(); i++) {
                    WatermarkBox watermarkBox = getWatermarkBox(watermarkType, i);
                    int watermarkListHeight = (int) watermarkBox.getHeight();
                    float startY = (getFileHeight(i) - watermarkListHeight) / 2;
                    if (watermarkConfig.getCenterLocationType() == CenterLocationTypeEnum.TOP_CENTER) {
                        startY = 0;
                    } else if (watermarkConfig.getCenterLocationType() == CenterLocationTypeEnum.BOTTOM_CENTER) {
                        startY = getFileHeight(i) - watermarkListHeight;
                    }
                    for (int j = 0; j < watermarkTextList.size(); j++) {
                        String curWatermarkText = watermarkTextList.get(j);
                        Point point = calcCenterWatermarkPoint(curWatermarkText, i);
                        drawString(point.getX(), startY + (j * getStringHeight()), curWatermarkText, i);
                    }
                }
                break;
            case IMAGE:
                for (int i = 0; i < sectionWrapperList.size(); i++) {
                    Point point = calcCenterWatermarkPoint(getWatermarkImageWidth(), getWatermarkImageHeight(), i);
                    drawImage(point.getX(), point.getY(), watermarkImage, i);
                }
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
        initWatermarkShapeStyle();
        SectionWrapper sectionWrapper = sectionWrapperList.get(pageNumber);
        SectPr.PgMar pgMar = sectionWrapper.getSectPr().getPgMar();
        watermarkShapeStyle.append(setLocation(pgMar.getHeader().floatValue(), pgMar.getLeft().floatValue(), x, y));
        watermarkShapeStyle.append(String.format(DocxConstant.WATERMARK_SIZE, getStringWidth(text), getStringHeight()));
        CTShape normalShape = DocxUtils.createNormalShape(watermarkConfig.getColor());
        normalShape.setStyle(watermarkShapeStyle.toString());
        addTextWatermark(DocxUtils.createNormalTextPath(text, font.getFontName()), normalShape, pageNumber);
    }

    @Override
    public void drawMultiLineString(float x, float y, List<String> text, int pageNumber) {
        String watermarkText = String.join(StringConstant.WARP, text);
        drawString(x, y, watermarkText, pageNumber);
    }

    @Override
    public void drawImage(float x, float y, byte[] data, int pageNumber) {
        // If there is a margin: x and y needs to delete the margin
        SectPr.PgMar pgMar = sectionWrapperList.get(pageNumber).getSectPr().getPgMar();
        float realX = x - pgMar.getLeft().floatValue() / TWIP_TO_POINT;
        float realY = y - pgMar.getTop().floatValue() / TWIP_TO_POINT;
        try {
            if (log.isDebugEnabled()) {
                log.debug("Draw image, page number: {}, x: {}pt(real: {}pt), y: {}pt(real: {}pt).(need delete the margin)",
                        pageNumber, x, realX, y, realY);
            }
            HeaderPart headerPart = watermarkHeaderPartList.get(pageNumber);
            BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(this.file, headerPart, data);
            Inline imageInline = imagePart.createImageInline(EASY_WATERMARK_FRAMEWORK, EASY_WATERMARK_FRAMEWORK, 111L, 111, false);
            R r = new R();
            Drawing drawing = new Drawing();
            r.getContent().add(drawing);
            drawing.getAnchorOrInline().add(DocxUtils.createAnchorByInline(imageInline, realX, realY));
            watermarkPList.get(pageNumber).getContent().add(r);
        } catch (Exception e) {
            log.error("Draw image error.", e);
            throw new DocxWatermarkHandlerException("Draw image error.", e);
        }
    }

    @Override
    public void rotate(float angle, float x, float y, int pageNumber) {

    }

    /**
     * add easy watermark header file and return relationship
     *
     * @param wordprocessingMLPackage file
     * @param hdr                     header
     * @param pageNumber              page number
     * @return docx relationship
     */
    private Relationship createEasyWatermarkRelationship(WordprocessingMLPackage wordprocessingMLPackage, Hdr hdr, Integer pageNumber) {
        try {
            HeaderPart headerPart = new HeaderPart(new PartName("/word/easy-watermark-header" + pageNumber + ".xml"));
            headerPart.setJaxbElement(hdr);
            watermarkHeaderPartList.add(pageNumber, headerPart);
            return wordprocessingMLPackage.getMainDocumentPart().addTargetPart(headerPart);
        } catch (Exception e) {
            log.error("Add easy watermark header error.", e);
            throw new DocxWatermarkHandlerException("Add easy watermark header error.", e);
        }
    }

    /**
     * add text watermark
     *
     * @param textPath   textPath
     * @param shape      shape
     * @param pageNumber page number
     */
    private void addTextWatermark(CTTextPath textPath, CTShape shape, Integer pageNumber) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Draw text, page number: {}, shape style:'{}'", pageNumber, shape.getStyle());
            }
            P p = watermarkPList.get(pageNumber);
            R watermarkHeader = DocxUtils.createR(textPath, shape, watermarkConfig.getAlpha());
            p.getContent().add(watermarkHeader);
        } catch (Exception e) {
            log.error("Add watermark error.", e);
            throw new DocxWatermarkHandlerException("Add watermark error.", e);
        }
    }

    private void checkPageInfoList() {
        if (this.sectionWrapperList == null || this.sectionWrapperList.isEmpty()) {
            throw new DocxWatermarkHandlerException("Page info list is empty.");
        }
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
     * @param x                draw x, unit pt
     * @param y                draw y, unit pt
     * @return watermark location:'margin-left:-70pt;margin-top:-30pt;'
     */
    private String setLocation(float headerTopMargin, float headerLeftMargin, float x, float y) {
        // reset origin
        float leftMargin = -headerLeftMargin / TWIP_TO_POINT;
        float topMargin = -headerTopMargin / TWIP_TO_POINT;
        // set watermark location
        leftMargin += x;
        topMargin += y;
        return String.format(DocxConstant.WATERMARK_LOCATION, leftMargin, topMargin);
    }
}
