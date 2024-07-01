package org.easywatermark.word;

import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.vml.CTFill;
import org.docx4j.vml.CTShape;
import org.docx4j.vml.CTTextPath;
import org.docx4j.vml.wordprocessingDrawing.CTWrap;
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
        Relationship rel = wordprocessingMLPackage.getMainDocumentPart()
                .addTargetPart(headerPart);

        Hdr hdr = getHdr(wordprocessingMLPackage, headerPart);
        headerPart.setJaxbElement(hdr);

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

    public static Hdr getHdr(WordprocessingMLPackage wordprocessingMLPackage,
                             Part sourcePart) throws Exception {

        Hdr hdr = factory.createHdr();

        String openXML = "<w:p xmlns:v=\"urn:schemas-microsoft-com:vml\"\n" +
                "     xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\"\n" +
                "     xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
                "     xmlns:w10=\"urn:schemas-microsoft-com:office:word\">\n" +
                "\t<w:pPr>\n" +
                "\t\t<w:pStyle w:val=\"Header\"/>\n" +
                "\t</w:pPr>\n" +
                "\t<w:sdt>\n" +
                "\t\t<w:sdtPr>\n" +
                "\t\t\t<w:id w:val=\"-1589924921\"/>\n" +
                "\t\t\t<w:lock w:val=\"sdtContentLocked\"/>\n" +
                "\t\t\t<w:docPartObj>\n" +
                "\t\t\t\t<w:docPartGallery w:val=\"Watermarks\"/>\n" +
                "\t\t\t\t<w:docPartUnique/>\n" +
                "\t\t\t</w:docPartObj>\n" +
                "\t\t</w:sdtPr>\n" +
                "\t\t<w:sdtEndPr/>\n" +
                "\t\t<w:sdtContent>\n" +
                "\t\t\t<w:r>\n" +
                "\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t<w:noProof/>\n" +
                "\t\t\t\t\t<w:lang w:eastAsia=\"zh-TW\"/>\n" +
                "\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t<w:pict>\n" +
                "\t\t\t\t\t<v:shape fillcolor=\"silver\"\n" +
                "\t\t\t\t\t         id=\"PowerPlusWaterMarkObject357476642\"\n" +
                "\t\t\t\t\t         o:allowincell=\"f\"\n" +
                "\t\t\t\t\t         o:spid=\"_x0000_s2049\"\n" +
                "\t\t\t\t\t         stroked=\"f\"\n" +
                "\t\t\t\t\t         style=\"position:absolute;margin-left:0;margin-top:0;width:527.85pt;height:131.95pt;rotation:315;z-index:-251658752;mso-position-horizontal:center;mso-position-horizontal-relative:margin;mso-position-vertical:center;mso-position-vertical-relative:margin\"\n" +
                "\t\t\t\t\t         type=\"#_x0000_t136\">\n" +
                "\t\t\t\t\t\t<v:fill opacity=\"1\"/>\n" +
                "\t\t\t\t\t\t<v:textpath string=\"MY WATERMARK\"\n" +
                "\t\t\t\t\t\t            style=\"font-family:&quot;Calibri&quot;;font-size:1pt\"/>\n" +
                "\t\t\t\t\t\t<w10:wrap anchorx=\"margin\"\n" +
                "\t\t\t\t\t\t          anchory=\"margin\"/>\n" +
                "\t\t\t\t\t</v:shape>\n" +
                "\t\t\t\t</w:pict>\n" +
                "\t\t\t</w:r>\n" +
                "\t\t</w:sdtContent>\n" +
                "\t</w:sdt>\n" +
                "</w:p>";
        System.out.println("------------");
        System.out.println(openXML);
        System.out.println("------------");
        P p = (P) XmlUtils.unmarshalString(openXML);
        hdr.getContent().add(p);
        return hdr;

    }

    public void addWaterMark() throws Exception {

        WordprocessingMLPackage wmlPackage = WordprocessingMLPackage
                .createPackage();

        Relationship relationship = createHeaderPart(wmlPackage);
        createHeaderReference(wmlPackage, relationship);
        File f = new File(System.getProperty("user.dir") + "/waterMarksample.docx");
        wmlPackage.save(f);

    }

    private void qq() {
        R r = new R();
        Pict pict = new Pict();
        CTShape ctShape = new CTShape();
        List<JAXBElement<?>> egShapeElements = ctShape.getEGShapeElements();

        // 上下居中
        CTWrap ctWrap = new CTWrap();

        // 水印样式 （文字、字体、字体大小）
        CTTextPath ctTextPath = new CTTextPath();
        ctTextPath.setString("MY WATERMARK");
        ctTextPath.setStyle("font-family:\"Calibri\";font-size:1pt");

        CTFill ctFill = new CTFill();
    }
}
