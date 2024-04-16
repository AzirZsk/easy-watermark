package org.azir.easywatermark.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.blend.BlendMode;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.azir.easywatermark.core.AbstractWatermarkHandler;
import org.azir.easywatermark.core.CustomDraw;
import org.azir.easywatermark.core.config.FontConfig;
import org.azir.easywatermark.core.config.WatermarkConfig;
import org.azir.easywatermark.entity.WatermarkBox;
import org.azir.easywatermark.enums.EasyWatermarkTypeEnum;
import org.azir.easywatermark.exception.LoadFileException;
import org.azir.easywatermark.exception.PdfWatermarkException;

import java.io.IOException;
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

    private PDFont font;

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
        // todo init font
    }

    @Override
    protected int getFileWidth(int page) {
        return (int) pdDocument.getPage(page).getMediaBox().getWidth();
    }

    @Override
    protected int getFileHeight(int page) {
        return (int) pdDocument.getPage(page).getMediaBox().getHeight();
    }

    @Override
    public void customDraw(CustomDraw customDraw) {
        PDPageContentStream pdPageContentStream = graphicsList.get(0);
    }

    @Override
    public void drawCenterWatermark() {

    }

    @Override
    public void drawOverspreadWatermark() {

    }

    @Override
    public void drawDiagonalWatermark() {

    }

    @Override
    public byte[] execute(EasyWatermarkTypeEnum watermarkType) {
        return new byte[0];
    }

    @Override
    public void loadFile(byte[] data) {
        try (PDDocument pdDocument = PDDocument.load(data)) {
            this.pdDocument = pdDocument;
        } catch (IOException e) {
            log.error("Load file error.", e);
            throw new LoadFileException("Load file error.", e);
        }
    }

    @Override
    public void close() throws IOException {
        pdDocument.close();
        for (PDPageContentStream pdPageContentStream : graphicsList) {
            pdPageContentStream.close();
        }
    }

    @Override
    public int getStringWidth(String text) {
        return 0;
    }

    @Override
    public int getStringHeight() {
        return 0;
    }

    @Override
    public WatermarkBox getStringBox(String... text) {
        return null;
    }

    @Override
    public void drawString(float x, float y, String text) {

    }

    @Override
    public void drawMultiLineString(float x, float y, List<String> text) {

    }

    @Override
    public void drawImage(float x, float y, byte[] data) {

    }
}
