# EasyWatermark

[中文文档](README_zh.md) <br/>
[Image Watermark Example](doc/ImageWatermarkExample.md) <br/>

Easy Watermark is a simple and easy-to-use watermark addition framework. The framework abstracts the methods of adding
watermarks for various file types. With just a few lines of code, you can add the same style of watermark to different
types of files.

# Quick Start

## 1. Add Dependency

```xml
<dependency>
    <groupId>org.easywatermark</groupId>
    <artifactId>easy-watermark</artifactId>
    <version>latest version</version>
</dependency>
```

## 2. Add Watermark

```java
byte[] result = EasyWatermark.create()
        .file(fileData)
        .text("Easy-Watermark")
        .execute();
```

# More Configurations

## Preset Watermark Types

- **CUSTOM:** Custom watermark, using the unified method abstracted by the framework, special processing is performed on
  each page of the incoming file (under development)
- **CENTER:** Centered, added at various centered positions on the page.
    - **VERTICAL_CENTER:** The watermark is vertically centered
    - **LEFT_CENTER:** Left centered
    - **RIGHT_CENTER:** Right centered
    - **TOP_CENTER:** Top centered
    - **BOTTOM_CENTER:** Bottom centered
- **OVERSPREAD:** Full page, the tilt angle can be set
    - **LOW:** Low spread, watermark content accounts for 33%
    - **NORMAL:** Generally spread, watermark content accounts for 66%
    - **HIGH:** High spread, watermark content accounts for 90%
- **DIAGONAL:** Diagonal watermark on the page
    - **TOP_TO_BOTTOM:** Text from top to bottom
    - **BOTTOM_TO_TOP:** Text from bottom to top

## Watermark Configuration

Corresponding to the `WatermarkConfig` class, this class will set various properties of the watermark.

```java
public class WatermarkConfig {
    // Watermark color
    private Color color = Color.BLACK;
    // Ignore rotation, still in development
    private boolean ignoreRotation = true;
    // Watermark transparency
    private float alpha = 1;
    // Subtype of spread watermark
    private OverspreadTypeEnum overspreadType = OverspreadTypeEnum.NORMAL;
    // Subtype of centered watermark
    private CenterLocationTypeEnum centerLocationType = CenterLocationTypeEnum.VERTICAL_CENTER;
    // Subtype of diagonal watermark
    private DiagonalDirectionTypeEnum diagonalDirectionType = DiagonalDirectionTypeEnum.TOP_TO_BOTTOM;

    /**
     * An angle, in degrees
     * The angle of clockwise rotation
     */
    private float angle = 0;
}
```

## Font Configuration

Corresponding to the `FontConfig` class, this class will set various properties of the watermark text.

```java
public class FontConfig {
    // Font color
    private Color color = Color.BLACK;
    // Font file
    private File fontFile;

    /**
     * The default font, currently only effective in image watermarks
     * Default font name is Dialog
     */
    private String fontName = "Dialog";
    // Font size
    private int fontSize = 12;

    /**
     * Font style: bold, italic, normal
     * @see Font#PLAIN
     * @see Font#BOLD
     * @see Font#ITALIC
     */
    private int fontStyle = Font.PLAIN;
}
```

# Advanced Usage

## Using Preset Watermark Types

```java
byte[] result = EasyWatermark.create()
        .file(fileData)
        .text("Easy-Watermark")
        // Choose different watermark types
        .easyWatermarkType(EasyWatermarkTypeEnum.CENTER)
        .execute();
```

## Custom Watermark Configuration and Font Configuration

```java
WatermarkConfig watermarkConfig = new WatermarkConfig();
// Set the transparency of the watermark
watermarkConfig.setAlpha(0.5f);

FontConfig fontConfig = new FontConfig();
// Set the size of the watermark text
fontConfig.setFontSize(30);

byte[] result = EasyWatermark.create()
        .file(fileData)
        // Custom configuration
        .config(watermarkConfig)
        .config(fontConfig)
        // Watermark type
        .easyWatermarkType(EasyWatermarkTypeEnum.CENTER)
        .text("Easy-Watermark")
        .executor();
```

# Development Plan

- [x] Add watermark to image files
- [x] Add watermark to PDF files
- [x] Improve user-defined watermark methods
- [ ] Add watermark to Word files
- [ ] Add watermark to Excel files
- [ ] Add watermark to PowerPoint files
- [ ] Hidden watermark

# Others

If you have more suggestions, feel free to raise an issue😊