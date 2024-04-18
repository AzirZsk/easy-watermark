package org.azir.easywatermark.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.fontbox.util.autodetect.FontFileFinder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.blend.BlendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.azir.easywatermark.core.AbstractWatermarkHandler;
import org.azir.easywatermark.core.CustomDraw;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.entity.Point;
import org.azir.easywatermark.enums.EasyWatermarkTypeEnum;
import org.azir.easywatermark.enums.FontTypeEnum;
import org.azir.easywatermark.enums.WatermarkTypeEnum;
import org.azir.easywatermark.exception.ImageWatermarkHandlerException;
import org.azir.easywatermark.exception.LoadFileException;
import org.azir.easywatermark.exception.PdfWatermarkException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangshukun
 * @date 2024/4/16
 */
@Slf4j
public class PdfWatermarkHandler extends AbstractWatermarkHandler<PDFont, List<PDPageContentStream>> {

    private PDDocument pdDocument;

    private List<PDPageContentStream> graphicsList;

    public PdfWatermarkHandler(byte[] data, FontConfig fontConfig, WatermarkConfig watermarkConfig) {
        super(data, fontConfig, watermarkConfig);
    }

    @Override
    protected void initGraphics() {
        int numberOfPages = pdDocument.getNumberOfPages();
        graphicsList = new ArrayList<>(numberOfPages);
        for (int i = 0; i < numberOfPages; i++) {
            try {
                PDExtendedGraphicsState gs = new PDExtendedGraphicsState();
                gs.setNonStrokingAlphaConstant(watermarkConfig.getAlpha());
                gs.setAlphaSourceFlag(true);
                gs.setBlendMode(BlendMode.MULTIPLY);

                PDPageContentStream contentStream = new PDPageContentStream(pdDocument, pdDocument.getPage(i),
                        PDPageContentStream.AppendMode.APPEND, true);
                contentStream.setGraphicsStateParameters(gs);
                contentStream.setNonStrokingColor(watermarkConfig.getColor());
                contentStream.setFont(font, (float) fontConfig.getFontSize());

                graphicsList.add(contentStream);
            } catch (IOException e) {
                log.error("Init graphics error.", e);
                throw new PdfWatermarkException("Init graphics error.", e);
            }
        }
    }

    @Override
    protected void initFont() {
        File fontFile = fontConfig.getFontFile();
        if (fontFile == null) {
            FontFileFinder fontFileFinder = new FontFileFinder();


            return;
        }
        FontTypeEnum fontType = FontTypeEnum.getFontType(fontFile);
        switch (fontType) {
            case TRUE_TYPE:
                try {
                    font = PDType0Font.load(pdDocument, fontFile);
                } catch (IOException e) {
                    log.error("Load font error.", e);
                    throw new LoadFileException("Load font error.", e);
                }
                break;
            case TYPE1:
                try {
                    font = new PDType1Font(pdDocument, Files.newInputStream(fontFile.toPath()));
                } catch (IOException e) {
                    log.error("Load font error.", e);
                    throw new LoadFileException("Load font error.", e);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + fontType);
        }
    }

    @Override
    protected float getFileWidth(int page) {
        return pdDocument.getPage(page).getMediaBox().getWidth();
    }

    @Override
    protected float getFileHeight(int page) {
        return pdDocument.getPage(page).getMediaBox().getHeight();
    }

    @Override
    public void customDraw(CustomDraw customDraw) {
    }

    @Override
    public void drawCenterWatermark() {
        WatermarkTypeEnum watermarkType = getWatermarkType();
        Point point;
        switch (watermarkType) {
            case SINGLE_TEXT:
                for (int i = 0; i < graphicsList.size(); i++) {
                    point = calcCenterWatermarkPoint(watermarkText, i);
                    drawString(point.getX(), point.getY(), watermarkText);
                }
                break;
            case MULTI_TEXT:
                break;
            case IMAGE:
                break;
            default:
                throw new ImageWatermarkHandlerException("Unsupported watermark type.");
        }
    }

    @Override
    public void drawOverspreadWatermark() {

    }

    @Override
    public void drawDiagonalWatermark() {

    }

    @Override
    public byte[] execute(EasyWatermarkTypeEnum watermarkType) {
        if (log.isDebugEnabled()) {
            log.debug("Add watermark. Watermark type:{}", watermarkType);
        }
        switch (watermarkType) {
            case CUSTOM:
                customDraw(customDraw);
                break;
            case CENTER:
                drawCenterWatermark();
                break;
            case DIAGONAL:
                drawDiagonalWatermark();
                break;
            case OVERSPREAD:
                drawOverspreadWatermark();
                break;
            default:
                throw new ImageWatermarkHandlerException("Unsupported watermark type.");
        }
        try {
            for (PDPageContentStream pdPageContentStream : graphicsList) {
                pdPageContentStream.close();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            pdDocument.save(byteArrayOutputStream);
            byte[] res = byteArrayOutputStream.toByteArray();
            log.info("Add watermark success.");
            return res;
        } catch (IOException e) {
            log.error("Save file error.", e);
            throw new PdfWatermarkException("Save file error.", e);
        }
    }

    @Override
    public void loadFile(byte[] data) {
        try {
            this.pdDocument = PDDocument.load(data);
        } catch (IOException e) {
            log.error("Load file error.", e);
            throw new LoadFileException("Load file error.", e);
        }
    }

    @Override
    public void close() throws IOException {
        pdDocument.close();
    }

    @Override
    public float getStringWidth(String text) {
        try {
            return font.getStringWidth(text) / 1000 * fontConfig.getFontSize();
        } catch (IOException e) {
            log.error("Get string width error.", e);
            throw new PdfWatermarkException("Get string width error.", e);
        }
    }

    @Override
    public float getStringHeight() {
        return font.getFontDescriptor().getCapHeight() / 1000 * fontConfig.getFontSize();
    }

    @Override
    public void drawString(float x, float y, String text) {
        for (int i = 0; i < graphicsList.size(); i++) {
            if (log.isDebugEnabled()) {
                log.debug("Draw string in page {}, x:{}, y:{}, text:{}", i, x, y, text);
            }
            PDPageContentStream pdPageContentStream = graphicsList.get(i);
            try {
                pdPageContentStream.beginText();
                pdPageContentStream.newLineAtOffset(x, y);
                pdPageContentStream.showText(text);
                pdPageContentStream.endText();
            } catch (IOException e) {
                log.error("Draw string error.", e);
                throw new PdfWatermarkException("Draw string error.", e);
            }
        }
    }

    @Override
    public void drawMultiLineString(float x, float y, List<String> text) {
        for (int i = 0; i < graphicsList.size(); i++) {
            if (log.isDebugEnabled()) {
                log.debug("Draw multi-line string in page {}, x:{}, y:{}, text:{}", i, x, y, text);
            }
            PDPageContentStream pdPageContentStream = graphicsList.get(i);
            try {
                pdPageContentStream.beginText();
                pdPageContentStream.newLineAtOffset(x, y);
                for (String s : text) {
                    pdPageContentStream.showText(s);
                    pdPageContentStream.newLine();
                }
                pdPageContentStream.endText();
            } catch (IOException e) {
                log.error("Draw multi-line string error.", e);
                throw new PdfWatermarkException("Draw multi-line string error.", e);
            }
        }
    }

    @Override
    public void drawImage(float x, float y, byte[] data) {
        PDImageXObject image = getPdImageXObject(data);
        for (int i = 0; i < graphicsList.size(); i++) {
            if (log.isDebugEnabled()) {
                log.debug("Draw image in page {}, x:{}, y:{}", i, x, y);
            }
            PDPageContentStream pdPageContentStream = graphicsList.get(i);
            try {
                pdPageContentStream.drawImage(image, x, y);
            } catch (IOException e) {
                log.error("Draw image error.", e);
                throw new PdfWatermarkException("Draw image error.", e);
            }
        }
    }

    private PDImageXObject getPdImageXObject(byte[] data) {
        try {
            return PDImageXObject.createFromByteArray(pdDocument, data, "image");
        } catch (IOException e) {
            log.error("Create image error.", e);
            throw new PdfWatermarkException("Create image error.", e);
        }
    }
}
