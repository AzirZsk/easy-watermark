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

    private static SectPr getSectPr(P paragraph) {
        if (paragraph.getPPr() != null && paragraph.getPPr().getSectPr() != null) {
            return paragraph.getPPr().getSectPr();
        }
        return null;
    }

    private static P qq() {
        ObjectFactory objectFactory2 = new ObjectFactory();
        org.docx4j.vml.ObjectFactory objectFactory = new org.docx4j.vml.ObjectFactory();
        org.docx4j.vml.wordprocessingDrawing.ObjectFactory objectFactory1 = new org.docx4j.vml.wordprocessingDrawing.ObjectFactory();

        PPr pPr = new PPr();

        PPrBase.PStyle pStyle = new PPrBase.PStyle();
        pPr.setPStyle(pStyle);
        pStyle.setVal("Header");
        pStyle.setParent(pPr);


        SdtRun sdtRun = new SdtRun();
        JAXBElement<SdtRun> pSdt = objectFactory2.createPSdt(sdtRun);

        P p = new P();
        p.setPPr(pPr);
        p.getContent().add(pSdt);


        SdtPr sdtPr = new SdtPr();
        sdtPr.setId();
        List<Object> rPrOrAliasOrLock = sdtPr.getRPrOrAliasOrLock();
        CTLock ctLock = new CTLock();
        ctLock.setVal(STLock.SDT_CONTENT_LOCKED);
        JAXBElement<CTLock> sdtPrLock = objectFactory2.createSdtPrLock(ctLock);
        rPrOrAliasOrLock.add(sdtPrLock);

        sdtRun.setSdtPr(sdtPr);


        CTSdtContentRun ctSdtContentRun = new CTSdtContentRun();


        sdtRun.setSdtContent(ctSdtContentRun);


        R r = new R();

        RPr rPr = new RPr();
        CTLanguage ctLanguage = new CTLanguage();
        ctLanguage.setEastAsia("zh-TW");
        rPr.setLang(ctLanguage);
        rPr.setNoProof(new BooleanDefaultTrue());
        r.setRPr(rPr);


        List<Object> content1 = ctSdtContentRun.getContent();
        content1.add(r);

        List<Object> content = r.getContent();


        Pict pict = new Pict();
        JAXBElement<Pict> rPict = objectFactory2.createRPict(pict);
        content.add(rPict);

        List<Object> anyAndAny = pict.getAnyAndAny();


        CTShape ctShape = new CTShape();
        ctShape.setFillcolor("silver");
        ctShape.setVmlId("adfasd");
        ctShape.setAllowincell(org.docx4j.vml.officedrawing.STTrueFalse.F);
        ctShape.setStroked(org.docx4j.vml.STTrueFalse.F);
        ctShape.setStyle("position:absolute;margin-left:0;margin-top:0;width:527.85pt;height:131.95pt;rotation:315;z-index:-251658752;mso-position-horizontal:center;mso-position-horizontal-relative:margin;mso-position-vertical:center;mso-position-vertical-relative:margin");
        ctShape.setSpid("_x0000_s2049");
        ctShape.setType("#_x0000_t136");


        JAXBElement<CTShape> shape = objectFactory.createShape(ctShape);

        anyAndAny.add(shape);


        List<JAXBElement<?>> egShapeElements = ctShape.getEGShapeElements();


        // 水印样式 （文字、字体、字体大小）
        CTTextPath ctTextPath = new CTTextPath();
        ctTextPath.setString("MY WATERMARK");
        ctTextPath.setStyle("font-family:\"Calibri\";font-size:1pt");
        JAXBElement<CTTextPath> textpath = objectFactory.createTextpath(ctTextPath);

        // 透明度
        CTFill ctFill = new CTFill();
        ctFill.setOpacity("0.5");
        JAXBElement<CTFill> fill = objectFactory.createFill(ctFill);


        // 上下居中
        CTWrap ctWrap = new CTWrap();
        ctWrap.setAnchorx(STHorizontalAnchor.MARGIN);
        ctWrap.setAnchory(STVerticalAnchor.MARGIN);
        JAXBElement<CTWrap> wrap = objectFactory1.createWrap(ctWrap);


        egShapeElements.add(fill);
        egShapeElements.add(textpath);
        egShapeElements.add(wrap);


        return p;
    }

    private static P qqqq() {
        ObjectFactory objectFactory2 = new ObjectFactory();
        org.docx4j.vml.ObjectFactory objectFactory = new org.docx4j.vml.ObjectFactory();
        org.docx4j.vml.wordprocessingDrawing.ObjectFactory objectFactory1 = new org.docx4j.vml.wordprocessingDrawing.ObjectFactory();
        org.docx4j.vml.officedrawing.ObjectFactory objectFactory3 = new org.docx4j.vml.officedrawing.ObjectFactory();

        CTFill ctFill = new CTFill();
        ctFill.setOn(org.docx4j.vml.STTrueFalse.T);
        ctFill.setOpacity("1");
        ctFill.setSrc("data:image/png;base64,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u3btWWedpW43Njbqjj799NPnnHOOun399de/+eab6NFIJOL3+9XtZcuWvfHGG+jRnp6e2bNnq9s33HDD66+/jh598skn586dq27feOON6CAEgMcff1wbisuXL9+zZw969LHHHps/f766fcstt7z66qvo0e7u7qqqKnW7paUlHo+jR8Ph8IIFC9TtFStW7Ny5Ez368MMPL1y4UB2ft99++8svv6zuV/c89NBDixYtUrdXrlz50ksvoQLigQceuOiii9Ttu+6668UXX0Rrvu+++y699FJ1+5577tm2bRt6tLOzc/Hixep2e3v75s2b0aMdHR11dXXq9o9+9KMXXngBPXrvvfdeccUVWsm+vj70aFtbWyAQULd//OMfP/fcc+jRu++++8orrwQAn8/3k5/8ZMOGDejRlStXXnXVVer2Qw89tH79evToHXfcsWTJEnX7P/7jP375y1+iR1esWHHttdeq24888si6devQo62trQ0NDep2d3d3b28verS5ubmxsVHd/tnPfvaf//mf6NGbb775+uuvV7dXr14diUTQozfddNOyZcvU7SeeeOKpp57SDglOkeFREwwjjBKHYMRwaT5ZWlV5/eS3Bj5c7aeR5zuF8ZdNRIfZkVzDkoxFIJwNkDiB4W2VU12Dv6+KuZfyDmeTpQ29BnKseAfGUwrF5VblEEaeP3G/lIB5ihVNEKwqg+ghOVDsI0pHk7MXhzbaze6XeJ8crB0uNoXCIYh+PWt+Vc5cziKBHfckPtSlBMxrTAhB/rsr55LTOJetWcz3DhdnFpZLO9U4iWOYWzZXPCtpPA479GStq4vZKDYry9iZlZL8wpw5bJhhK4eCm3DGcyVsbGpzctjnO6ZfoGA4YopZoXANWifn3DmV8wZYwFqb5TgvGEwHRvBZlx5/OZ1lx7yEhtn+zO2ENEwn9iaG2azEPyKjSQWDlegwz8uX5OPRPjyzi1HG5clpKqXOazAcC4xlNmgxR5olcQVbX5uT7mFHYRtchpmYtBOdwHCNkEcGBk+nAekTGXKJSAGTm09uSnjgj8XTPIMuC0EviGMaPA9s4lwwzJLhLCnxLF755KZEh+HDieftBt55wuWwJTTXJE+TDOWaFHwFgBSCucem74+zBhdga1vuN5KY/Mz/ahxTAk5Kw/xFCsEco8XW8Z1g5k33LoiYdDacpzjdKs5Lo30lVmDJMGC+I4VgjjFUTNi2LVEOOuGJw69OdId5QWM1u/oNJbeqqyQnSCGYTxhOS4dS81CpyplVp7XEfUXJptIn1bpiQwpBL6ITHIZGMbGAqMmM65WOXk4s7JCIoWkvlcFiQKbIuIdhkhpDjuQwFYPWbFNpMR5MXdSR8zQjSa4w/clNiTVsPmzw9N3cwi9N3GkG+/nBSH5GtUJaUrqUg4WNNIcdR7ifLlfS0GtSGG0P3smMtSs8rga27S8pJLylXxQYtL5lLIPjXJ8gfGaya7aQV4z+HeHNtmDVMhogLd8iR2qCjkBUTOzPsVwtBaFFQmjali7y4GjDeALWWmHnWiLJX6QQdJz8SqZlaK9EK5IzUuxEJ5iyamlIS0giAyMO4oTsc0GeGoYC8OVl2roXL7jS+EMcOYy5S7yD1AQdwYLio3OfuUmeakP8kRDiuVICFjnaGJCaoF0Kxq2O5pF4IS/EgpAyTCQyXHooKRLQkSA1QesYZmnkBRZkje5vOhH8pV2C3RLaIYbHUErAIiedTkshaBEL09IQhyzidDaMS6MbhiqV5gcU2liq8OIU1pwREin+JBpSCNrFVJaG++CN4Umdo+FmYMGX/RIdGmZ7W4o/iQ4pBG3Bk6WB45qU5LwQriUxTnc0/YVoWXNm4RgekuJPQkQGRrjgWc6hK29tvgmcpUSBgueyGFZClHqC2igGL4RxJPmIGkn7/0Ynx+99wKSjAAAAAElFTkSuQmCC");
//        ctFill.setType(STFillType.FRAME);
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

        WordprocessingMLPackage wmlPackage = WordprocessingMLPackage.createPackage();
        Relationship relationship = createHeaderPart(wmlPackage);
        createHeaderReference(wmlPackage, relationship);
        File f = new File(System.getProperty("user.dir") + "/waterMarksample1.docx");
        wmlPackage.save(f);
    }
}
