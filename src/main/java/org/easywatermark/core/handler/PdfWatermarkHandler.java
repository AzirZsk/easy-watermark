package org.easywatermark.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.blend.BlendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import org.easywatermark.core.AbstractWatermarkHandler;
import org.easywatermark.core.EasyWatermarkCustomDraw;
import org.easywatermark.core.config.FontConfig;
import org.easywatermark.core.config.WatermarkConfig;
import org.easywatermark.entity.PageInfo;
import org.easywatermark.entity.Point;
import org.easywatermark.entity.WatermarkBox;
import org.easywatermark.enums.*;
import org.easywatermark.exception.ImageWatermarkHandlerException;
import org.easywatermark.exception.LoadFileException;
import org.easywatermark.exception.PdfWatermarkHandlerException;
import org.easywatermark.utils.EasyWatermarkUtils;

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
                throw new PdfWatermarkHandlerException("Init graphics error.", e);
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
        this.ascent = font.getFontDescriptor().getAscent() / 1000 * fontConfig.getFontSize();
        if (getWatermarkType() == WatermarkTypeEnum.IMAGE) {
            this.pdWatermarkImage = getPdImageXObject(watermarkImage);
        }
        if (log.isDebugEnabled()) {
            for (int i = 0; i < pdDocument.getNumberOfPages(); i++) {
                log.debug("Page {} width:{}, height:{}", i, getFileWidth(i), getFileHeight(i));
            }
            log.debug("ascent:{}, string height:{}", ascent, getStringHeight());
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
    public void customDraw(EasyWatermarkCustomDraw easyWatermarkCustomDraw) {
        for (int i = 0; i < graphics.size(); i++) {
            PageInfo pageInfo = new PageInfo(getFileWidth(i), getFileHeight(i), i);
            easyWatermarkCustomDraw.draw(pageInfo, this, this);
        }
    }

    @Override
    public void drawCenterWatermark() {
        WatermarkTypeEnum watermarkType = getWatermarkType();
        Point point;
        switch (watermarkType) {
            case SINGLE_TEXT:
                for (int i = 0; i < graphics.size(); i++) {
                    point = calcCenterWatermarkPoint(watermarkText, i);
                    drawString(point.getX(), point.getY(), watermarkText, i);
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
                    for (int j = 0; j < watermarkTextList.size(); j++) {
                        String curWatermarkText = watermarkTextList.get(j);
                        point = calcCenterWatermarkPoint(curWatermarkText, i);
                        drawString(point.getX(), startY + (j * getStringHeight()), curWatermarkText, i);
                    }
                }
                break;
            case IMAGE:
                for (int i = 0; i < graphics.size(); i++) {
                    point = calcCenterWatermarkPoint(getWatermarkImageWidth(), getWatermarkImageHeight(), i);
                    drawImage(point.getX(), point.getY(), watermarkImage, i);
                }
                break;
            default:
                throw new ImageWatermarkHandlerException("Unsupported watermark type.");
        }
    }

    @Override
    public void drawOverspreadWatermark() {
        if (watermarkConfig.getAngle() != 0) {
            if (log.isDebugEnabled()) {
                log.debug("overspread angle:{}", watermarkConfig.getAngle());
            }
            drawOverspreadWatermarkForAngle(watermarkConfig.getAngle());
            return;
        }
        for (int pageNumber = 0; pageNumber < graphics.size(); pageNumber++) {
            drawOverspreadWatermark(pageNumber);
        }
    }

    @Override
    public void drawDiagonalWatermark() {
        for (int i = 0; i < graphics.size(); i++) {
            float fileWidth = getFileWidth(i);
            float fileHeight = getFileHeight(i);
            double radians = EasyWatermarkUtils.calcRadians(fileWidth, fileHeight);
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
            PDPageContentStream pdPageContentStream = graphics.get(i);
            rotatePageByRadians(pdPageContentStream, (float) radians, fileWidth / 2, fileHeight / 2);
            WatermarkTypeEnum watermarkType = getWatermarkType();
            switch (watermarkType) {
                case SINGLE_TEXT:
                    directDrawString(-getStringWidth(watermarkText) / 2, -getStringHeight() + ascent, watermarkText, pdPageContentStream);
                    break;
                case MULTI_TEXT:
                    WatermarkBox watermarkBox = getWatermarkBox(watermarkType, i);
                    for (int j = 0; j < watermarkTextList.size(); j++) {
                        String curWatermarkText = watermarkTextList.get(j);
                        float tx = -getStringWidth(curWatermarkText) / 2;
                        float ty = (watermarkBox.getHeight() + ascent) / 2 - getStringHeight() - j * getStringHeight();
                        directDrawString(tx, ty, curWatermarkText, pdPageContentStream);
                    }
                    break;
                case IMAGE:
                    directDrawImage(-getWatermarkImageWidth() / 2, -getWatermarkImageHeight() / 2, watermarkImage, pdPageContentStream);
                    break;
                default:
                    throw new ImageWatermarkHandlerException("Unsupported watermark type.");
            }
        }
    }

    @Override
    public byte[] export(EasyWatermarkTypeEnum watermarkType) {
        try {
            for (PDPageContentStream pdPageContentStream : graphics) {
                pdPageContentStream.close();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            pdDocument.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error("Save file error.", e);
            throw new PdfWatermarkHandlerException("Save file error.", e);
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
            throw new PdfWatermarkHandlerException("Get string width error.", e);
        }
    }

    @Override
    public float getStringHeight() {
        return font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontConfig.getFontSize();
    }

    @Override
    public void drawString(float x, float y, String text, int pageNumber) {
        if (log.isDebugEnabled()) {
            log.debug("Draw string in page {}, x:{}, y:{}, text:{}", pageNumber, x, y, text);
        }
        PDPageContentStream pdPageContentStream = graphics.get(pageNumber);
        try {
            pdPageContentStream.beginText();
            pdPageContentStream.newLineAtOffset(x, getFileHeight(pageNumber) - y - ascent);
            pdPageContentStream.showText(text);
            pdPageContentStream.endText();
        } catch (IOException e) {
            log.error("Draw string error.", e);
            throw new PdfWatermarkHandlerException("Draw string error.", e);
        }
    }

    @Override
    public void drawMultiLineString(float x, float y, List<String> text, int pageNumber) {
        if (log.isDebugEnabled()) {
            log.debug("Draw multi-line string in page {}, x:{}, y:{}, text:{}", pageNumber, x, y, text);
        }
        PDPageContentStream pdPageContentStream = graphics.get(pageNumber);
        try {
            pdPageContentStream.beginText();
            pdPageContentStream.newLineAtOffset(x, getFileHeight(pageNumber) - y - ascent);
            for (String s : text) {
                pdPageContentStream.showText(s);
                pdPageContentStream.newLineAtOffset(0, -getStringHeight());
            }
            pdPageContentStream.endText();
        } catch (IOException e) {
            log.error("Draw multi-line string error.", e);
            throw new PdfWatermarkHandlerException("Draw multi-line string error.", e);
        }

    }

    @Override
    public void drawImage(float x, float y, byte[] data, int pageNumber) {
        PDImageXObject image = getPdImageXObject(data);
        if (log.isDebugEnabled()) {
            log.debug("Draw image in page {}, x:{}, y:{}", pageNumber, x, y);
        }
        PDPageContentStream pdPageContentStream = graphics.get(pageNumber);
        try {
            pdPageContentStream.drawImage(image, x, getFileHeight(pageNumber) - y - image.getHeight());
        } catch (IOException e) {
            log.error("Draw image error.", e);
            throw new PdfWatermarkHandlerException("Draw image error.", e);
        }
    }

    @Override
    public void rotate(float angle, float x, float y, int pageNumber) {
        if (log.isDebugEnabled()) {
            log.debug("Rotate page {}, angle:{}, x:{}, y:{}", pageNumber, angle, x, y);
        }
        PDPageContentStream pdPageContentStream = graphics.get(pageNumber);
        rotatePage(pdPageContentStream, angle, x, y);
    }

    /**
     * draw overspread watermark for angle
     *
     * @param angle angle
     */
    private void drawOverspreadWatermarkForAngle(float angle) {
        for (int i = 0; i < graphics.size(); i++) {
            WatermarkBox watermarkBox = getWatermarkBox(getWatermarkType(), i);
            OverspreadTypeEnum overspreadType = watermarkConfig.getOverspreadType();
            float coverage = overspreadType.getCoverage();
            float curPageWidth = getFileWidth(i);
            float curPageHeight = getFileHeight(i);
            // calculate the maximum width and height of the page after rotation
            if (curPageWidth < curPageHeight) {
                curPageWidth = curPageHeight;
            } else if (curPageHeight < curPageWidth) {
                curPageHeight = curPageWidth;
            }

            float watermarkWidth = coverage * curPageWidth;
            int columns = (int) (watermarkWidth / watermarkBox.getWidth());
            float blankWidth = curPageWidth - columns * watermarkBox.getWidth();
            // calculate the distance between watermarks
            float k = curPageWidth * 0.01f;
            float widthWatermarkDistance = calcDistanceBetweenWatermarks(blankWidth, k, columns);
            float widthWatermarkDistanceFromPageBorder = widthWatermarkDistance - k;

            float watermarkHeight = coverage * curPageHeight;
            int rows = (int) (watermarkHeight / watermarkBox.getHeight());
            float blankHeight = curPageHeight - rows * watermarkBox.getHeight();
            // calculate the distance between watermarks
            float heightWatermarkDistance = calcDistanceBetweenWatermarks(blankHeight, k, rows);
            float heightWatermarkDistanceFromPageBorder = heightWatermarkDistance - k;

            PDPageContentStream pdPageContentStream = graphics.get(i);
            float newOriginX = getFileWidth(i) / 2;
            float newOriginY = getFileHeight(i) / 2;
            // The PDF rotates counterclockwise, so it needs to be reversed
            rotatePage(pdPageContentStream, -angle, newOriginX, newOriginY);
            // Why x/y add or reduce half of the width or height?
            // Because after rotating the page, there may be airstrikes in the four corners of the page
            float x = widthWatermarkDistanceFromPageBorder - getFileWidth(i) / 2;
            for (; x < getFileWidth(i) * 3 / 2; x += watermarkBox.getWidth() + widthWatermarkDistance) {
                float y = getFileHeight(i) - heightWatermarkDistanceFromPageBorder - watermarkBox.getHeight() + getFileHeight(i) / 2;
                for (; y > 0 - getFileHeight(i) / 2; y -= watermarkBox.getHeight() + heightWatermarkDistance) {
                    switch (getWatermarkType()) {
                        case SINGLE_TEXT:
                            directDrawString(x - newOriginX, y - newOriginY, watermarkText, pdPageContentStream);
                            break;
                        case MULTI_TEXT:
                            directDrawMultiString(x - newOriginX, y - newOriginY, watermarkTextList, pdPageContentStream);
                            break;
                        case IMAGE:
                            directDrawImage(x - newOriginX, y - newOriginY, super.watermarkImage, pdPageContentStream);
                            break;
                        default:
                            throw new PdfWatermarkHandlerException("Unsupported watermark type.");
                    }
                }
            }
        }
    }

    /**
     * rotate page
     *
     * @param graphics graphics
     * @param angle    angle
     * @param originX  new origin x
     * @param originY  new origin y
     */
    private void rotatePage(PDPageContentStream graphics, float angle, float originX, float originY) {
        double radians = Math.toRadians(angle);
        rotatePageByRadians(graphics, (float) radians, originX, originY);
    }

    /**
     * rotate page
     *
     * @param graphics graphics
     * @param radians  radians
     * @param originX  new origin x
     * @param originY  new origin y
     */
    private void rotatePageByRadians(PDPageContentStream graphics, float radians, float originX, float originY) {
        Matrix rotateInstance = Matrix.getRotateInstance(radians, originX, originY);
        try {
            graphics.transform(rotateInstance);
        } catch (IOException e) {
            log.error("Rotate page error.", e);
            throw new PdfWatermarkHandlerException("Rotate page error.", e);
        }
    }

    /**
     * direct draw string.
     *
     * @param x         x
     * @param y         y
     * @param watermark watermark
     */
    private void directDrawString(float x, float y, String watermark, PDPageContentStream graphics) {
        if (log.isDebugEnabled()) {
            log.debug("Draw string x:{}, y:{}, text:{}", x, y, watermark);
        }
        try {
            graphics.beginText();
            graphics.newLineAtOffset(x, y);
            graphics.showText(watermark);
            graphics.endText();
        } catch (IOException e) {
            log.error("Draw string error.", e);
            throw new PdfWatermarkHandlerException("Draw string error.", e);
        }
    }

    /**
     * direct draw multi-line string.
     *
     * @param x         x
     * @param y         y
     * @param watermark watermark
     */
    private void directDrawMultiString(float x, float y, List<String> watermark, PDPageContentStream graphics) {
        if (log.isDebugEnabled()) {
            log.debug("Draw multi-line string x:{}, y:{}, text:{}", x, y, watermark);
        }
        try {
            graphics.beginText();
            graphics.newLineAtOffset(x, y);
            for (String s : watermark) {
                graphics.showText(s);
                graphics.newLineAtOffset(0, -getStringHeight());
            }
            graphics.endText();
        } catch (IOException e) {
            log.error("Draw multi-line string error.", e);
            throw new PdfWatermarkHandlerException("Draw multi-line string error.", e);
        }
    }

    /**
     * direct draw image.
     *
     * @param x    x
     * @param y    y
     * @param data data
     */
    private void directDrawImage(float x, float y, byte[] data, PDPageContentStream graphics) {
        PDImageXObject image = getPdImageXObject(data);
        if (log.isDebugEnabled()) {
            log.debug("Draw image x:{}, y:{}", x, y);
        }
        try {
            graphics.drawImage(image, x, y);
        } catch (IOException e) {
            log.error("Draw image error.", e);
            throw new PdfWatermarkHandlerException("Draw image error.", e);
        }
    }

    private PDImageXObject getPdImageXObject(byte[] data) {
        try {
            return PDImageXObject.createFromByteArray(pdDocument, data, "image");
        } catch (IOException e) {
            log.error("Create image error.", e);
            throw new PdfWatermarkHandlerException("Create image error.", e);
        }
    }
}
