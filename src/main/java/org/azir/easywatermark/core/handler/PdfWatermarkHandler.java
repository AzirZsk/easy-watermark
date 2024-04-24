package org.azir.easywatermark.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.blend.BlendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import org.azir.easywatermark.core.AbstractWatermarkHandler;
import org.azir.easywatermark.core.CustomDraw;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.entity.Point;
import org.azir.easywatermark.entity.WatermarkBox;
import org.azir.easywatermark.enums.*;
import org.azir.easywatermark.exception.ImageWatermarkHandlerException;
import org.azir.easywatermark.exception.LoadFileException;
import org.azir.easywatermark.exception.PdfWatermarkException;
import org.azir.easywatermark.utils.EasyWatermarkUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Pdf watermark handler.
 * The origin is in the lower left corner
 *
 * @author zhangshukun
 * @date 2024/4/16
 */
@Slf4j
public class PdfWatermarkHandler extends AbstractWatermarkHandler<PDFont, List<PDPageContentStream>> {

    private PDDocument pdDocument;

    private PDImageXObject pdWatermarkImage;

    private float descent;

    private float ascent;

    public PdfWatermarkHandler(byte[] data, FontConfig fontConfig, WatermarkConfig watermarkConfig) {
        super(data, fontConfig, watermarkConfig);
    }

    @Override
    protected void initGraphics() {
        int numberOfPages = pdDocument.getNumberOfPages();
        graphics = new ArrayList<>(numberOfPages);
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

                graphics.add(contentStream);
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
            this.font = PDType1Font.HELVETICA;
            return;
        }
        FontTypeEnum fontType = FontTypeEnum.getFontType(fontFile);
        switch (fontType) {
            case TRUE_TYPE:
                try {
                    this.font = PDType0Font.load(pdDocument, fontFile);
                } catch (IOException e) {
                    log.error("Load font error.", e);
                    throw new LoadFileException("Load font error.", e);
                }
                break;
            case TYPE1:
                try {
                    this.font = new PDType1Font(pdDocument, Files.newInputStream(fontFile.toPath()));
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
    protected void initEnvironment() {
        this.descent = font.getFontDescriptor().getDescent() / 1000 * fontConfig.getFontSize();
        this.ascent = font.getFontDescriptor().getAscent() / 1000 * fontConfig.getFontSize();
        if (getWatermarkType() == WatermarkTypeEnum.IMAGE) {
            this.pdWatermarkImage = getPdImageXObject(watermarkImage);
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
    protected float getWatermarkImageWidth() {
        return pdWatermarkImage.getWidth();
    }

    @Override
    protected float getWatermarkImageHeight() {
        return pdWatermarkImage.getHeight();
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
                for (int i = 0; i < graphics.size(); i++) {
                    point = calcCenterWatermarkPoint(watermarkText, i);
                    point.setY(getFileHeight(i) - getStringHeight() - point.getY());
                    drawString(point.getX(), point.getY(), watermarkText);
                }
                break;
            case MULTI_TEXT:
                for (int i = 0; i < graphics.size(); i++) {
                    WatermarkBox watermarkBox = getWatermarkBox(watermarkType, i);
                    int watermarkListHeight = (int) watermarkBox.getHeight();
                    float startY = (getFileHeight(i) - watermarkListHeight) / 2;
                    if (watermarkConfig.getCenterLocationType() == CenterLocationTypeEnum.TOP_CENTER) {
                        startY = 0;
                    } else if (watermarkConfig.getCenterLocationType() == CenterLocationTypeEnum.BOTTOM_CENTER) {
                        startY = getFileHeight(i) - watermarkListHeight;
                    }
                    startY = getFileHeight(i) - getStringHeight() - startY;
                    for (int j = 0; j < watermarkTextList.size(); j++) {
                        String curWatermarkText = watermarkTextList.get(j);
                        point = calcCenterWatermarkPoint(curWatermarkText, i);
                        drawString(point.getX(), startY - (j * getStringHeight()), curWatermarkText);
                    }
                }
                break;
            case IMAGE:
                for (int i = 0; i < graphics.size(); i++) {
                    point = calcCenterWatermarkPoint(getWatermarkImageWidth(), getWatermarkImageHeight(), i);
                    drawImage(point.getX(), getFileHeight(i) - getWatermarkImageHeight() - point.getY(), watermarkImage);
                }
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
        for (int i = 0; i < graphics.size(); i++) {
            float fileWidth = getFileWidth(i);
            float fileHeight = getFileHeight(i);
            double radians = EasyWatermarkUtils.calcRadians(fileWidth, fileHeight);
            Matrix rotateInstance;
            DiagonalDirectionTypeEnum diagonalDirectionType = watermarkConfig.getDiagonalDirectionType();
            switch (diagonalDirectionType) {
                case TOP_TO_BOTTOM:
                    radians = -radians;
                    break;
                case BOTTOM_TO_TOP:
                    break;
                default:
                    throw new ImageWatermarkHandlerException("Unsupported diagonal watermark type.");
            }
            try {
                PDPageContentStream pdPageContentStream = graphics.get(i);
                rotateInstance = Matrix.getRotateInstance(radians, fileWidth / 2, fileHeight / 2);
                pdPageContentStream.transform(rotateInstance);
                WatermarkTypeEnum watermarkType = getWatermarkType();
                switch (watermarkType) {
                    case SINGLE_TEXT:
                        drawString(-getStringWidth(watermarkText) / 2, -getStringHeight() / 2, watermarkText);
                        break;
                    case MULTI_TEXT:
                        WatermarkBox watermarkBox = getWatermarkBox(watermarkType, i);
                        for (int j = 0; j < watermarkTextList.size(); j++) {
                            String curWatermarkText = watermarkTextList.get(j);
                            drawString(-getStringWidth(curWatermarkText) / 2, watermarkBox.getHeight() / 2 - getStringHeight() - j * getStringHeight(), curWatermarkText);
                        }
                        break;
                    case IMAGE:
                        break;
                    default:
                        throw new ImageWatermarkHandlerException("Unsupported watermark type.");
                }
            } catch (IOException e) {
                log.error("Draw diagonal watermark error.", e);
                throw new PdfWatermarkException("Draw diagonal watermark error.", e);
            }

        }
    }

    private Point aa(double radians, float hypotenuse) {
        float x, y;
        y = (float) (hypotenuse * Math.sin(Math.abs(radians)));
        x = (float) Math.sqrt(Math.pow(hypotenuse, 2) - Math.pow(y, 2));
        return new Point(x, y);
    }

    @Override
    public byte[] execute0(EasyWatermarkTypeEnum watermarkType) {
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
            for (PDPageContentStream pdPageContentStream : graphics) {
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
        return font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontConfig.getFontSize();
    }

    @Override
    public void drawString(float x, float y, String text) {
        for (int i = 0; i < graphics.size(); i++) {
            if (log.isDebugEnabled()) {
                log.debug("Draw string in page {}, x:{}, y:{}, text:{}", i, x, y, text);
            }
            PDPageContentStream pdPageContentStream = graphics.get(i);
            try {
                pdPageContentStream.beginText();
                pdPageContentStream.newLineAtOffset(x, y - descent);
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
        for (int i = 0; i < graphics.size(); i++) {
            if (log.isDebugEnabled()) {
                log.debug("Draw multi-line string in page {}, x:{}, y:{}, text:{}", i, x, y, text);
            }
            PDPageContentStream pdPageContentStream = graphics.get(i);
            try {
                pdPageContentStream.beginText();
                pdPageContentStream.newLineAtOffset(x, y - descent);
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
        for (int i = 0; i < graphics.size(); i++) {
            if (log.isDebugEnabled()) {
                log.debug("Draw image in page {}, x:{}, y:{}", i, x, y);
            }
            PDPageContentStream pdPageContentStream = graphics.get(i);
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
