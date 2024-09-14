package org.easywatermark.word;

import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.vml.*;
import org.docx4j.vml.wordprocessingDrawing.CTWrap;
import org.docx4j.vml.wordprocessingDrawing.STHorizontalAnchor;
import org.docx4j.vml.wordprocessingDrawing.STVerticalAnchor;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.util.List;


/**
 * @author zhangshukun
 * @since 2024/07/01
 */
public class Docx4jTest {


    static ObjectFactory factory = Context.getWmlObjectFactory();

    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("user.dir"));
        Docx4jTest sample = new Docx4jTest();
        sample.addWaterMark();
    }

    public static Relationship createHeaderPart(WordprocessingMLPackage wordprocessingMLPackage)
            throws Exception {

        HeaderPart headerPart = new HeaderPart();
        Hdr hdr = getHdr();
        headerPart.setJaxbElement(hdr);
        Relationship rel = wordprocessingMLPackage.getMainDocumentPart()
                .addTargetPart(headerPart);
        return rel;
    }

    public static void createHeaderReference(
            WordprocessingMLPackage wordprocessingMLPackage,
            Relationship relationship)
            throws InvalidFormatException {

        List<SectionWrapper> sections = wordprocessingMLPackage.getDocumentModel().getSections();

        SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
        // There is always a section wrapper, but it might not contain a sectPr
        if (sectPr == null) {
            sectPr = factory.createSectPr();
            wordprocessingMLPackage.getMainDocumentPart().addObject(sectPr);
            sections.get(sections.size() - 1).setSectPr(sectPr);
        }

        HeaderReference headerReference = factory.createHeaderReference();
        headerReference.setId(relationship.getId());
        headerReference.setType(HdrFtrRef.DEFAULT);
        sectPr.getEGHdrFtrReferences().add(headerReference);
    }

    public static Hdr getHdr() throws Exception {
        Hdr hdr = factory.createHdr();
        P qq = qqqq();
        System.out.println(XmlUtils.marshaltoString(qq));
        hdr.getContent().add(qq);
        return hdr;
    }

    private static P qqqq() {
        ObjectFactory objectFactory2 = new ObjectFactory();
        org.docx4j.vml.ObjectFactory objectFactory = new org.docx4j.vml.ObjectFactory();
        org.docx4j.vml.wordprocessingDrawing.ObjectFactory objectFactory1 = new org.docx4j.vml.wordprocessingDrawing.ObjectFactory();
        org.docx4j.vml.officedrawing.ObjectFactory objectFactory3 = new org.docx4j.vml.officedrawing.ObjectFactory();

        CTFill ctFill = new CTFill();
        ctFill.setOn(org.docx4j.vml.STTrueFalse.T);
        ctFill.setOpacity("1");
        JAXBElement<CTFill> fill = objectFactory.createFill(ctFill);

        CTStroke ctStroke = new CTStroke();
        ctStroke.setOn(org.docx4j.vml.STTrueFalse.F);
        JAXBElement<CTStroke> stroke = objectFactory.createStroke(ctStroke);

        org.docx4j.vml.officedrawing.CTLock ctLock = new org.docx4j.vml.officedrawing.CTLock();
        ctLock.setExt(STExt.VIEW);
        ctLock.setAspectratio(org.docx4j.vml.officedrawing.STTrueFalse.T);
        JAXBElement<org.docx4j.vml.officedrawing.CTLock> lock = objectFactory3.createLock(ctLock);

        CTTextPath ctTextPath = new CTTextPath();
        ctTextPath.setString("MY WATERMARK\nasd;lgkja;lsdjg;ljzx;lkcv");
        ctTextPath.setStyle("font-family:\"宋体\";");
        JAXBElement<CTTextPath> textpath = objectFactory.createTextpath(ctTextPath);

        CTShape ctShape = new CTShape();
        ctShape.setFillcolor("silver");
        ctShape.setVmlId("adfasd");
        ctShape.setStroked(org.docx4j.vml.STTrueFalse.F);
        ctShape.setStyle("position:absolute;left:0;top:0;width:468pt;height:300pt;");
        ctShape.setSpid("_x0000_s2049");
        ctShape.setSpt(136f);
        ctShape.setType("#_x0000_t136");


        List<JAXBElement<?>> egShapeElements = ctShape.getEGShapeElements();
        egShapeElements.add(fill);
        egShapeElements.add(stroke);
        egShapeElements.add(lock);
        egShapeElements.add(textpath);

        CTWrap ctWrap = new CTWrap();
        ctWrap.setAnchorx(STHorizontalAnchor.MARGIN);
        ctWrap.setAnchory(STVerticalAnchor.MARGIN);
        JAXBElement<CTWrap> wrap = objectFactory1.createWrap(ctWrap);


        Pict pict = new Pict();
        JAXBElement<CTShape> shape = objectFactory.createShape(ctShape);
        pict.getAnyAndAny().add(shape);

        R r = new R();
        JAXBElement<Pict> rPict = objectFactory2.createRPict(pict);
        r.getContent().add(rPict);

        P p = new P();
        p.getContent().add(r);
        return p;
    }

    public void addWaterMark() throws Exception {
        File f = new File(System.getProperty("user.dir") + "/waterMarksample1.docx");
        WordprocessingMLPackage wmlPackage = WordprocessingMLPackage.load(f);
        Relationship relationship = createHeaderPart(wmlPackage);
        createHeaderReference(wmlPackage, relationship);
        wmlPackage.save(f);
    }
}
