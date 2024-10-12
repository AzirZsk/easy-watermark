# 💧 EasyWatermark

[🌍 中文文档](README_zh.md) <br/>
[📷 Image Watermark Example](doc/ImageWatermarkExample.md) | [📄 Pdf Watermark Example](doc/PdfWatermarkExample.md) | [📝 Docx Watermark Example](doc/DocxWatermarkExample.md)

**EasyWatermark** is a simple and easy-to-use watermarking framework. It abstracts methods for adding watermarks to various file types. With just a few lines of code, you can add the same style of watermark to different types of files.

## 🚀 Quick Start

### 1️⃣ Add Dependency

```xml
<dependency>
    <groupId>org.easywatermark</groupId>
    <artifactId>easy-watermark</artifactId>
    <version>latest version</version>
</dependency>
```

### 2️⃣ Add Watermark

```java
public class WatermarkExample {

    public static void main(String[] args) {
        byte[] result = EasyWatermark.create()
                .file(fileData)
                .text("Easy-Watermark")
                .execute();
    }
    
}
```

## 🔧 More Configurations

### 🎯 Preset Watermark Types

- **CUSTOM:** Custom watermark (under development)
- **CENTER:** Centered watermark
    - **VERTICAL_CENTER:** Vertically centered
    - **LEFT_CENTER:** Left centered
    - **RIGHT_CENTER:** Right centered
    - **TOP_CENTER:** Top centered
    - **BOTTOM_CENTER:** Bottom centered
- **OVERSPREAD:** Full-page watermark with tilt angle options
    - **LOW:** Watermark covers 33%
    - **NORMAL:** Watermark covers 66%
    - **HIGH:** Watermark covers 90%
- **DIAGONAL:** Diagonal watermark
    - **TOP_TO_BOTTOM:** From top to bottom
    - **BOTTOM_TO_TOP:** From bottom to top

### ⚙️ Watermark Configuration

Use the `WatermarkConfig` class to set properties like opacity, color, font, and more.

```java
public class WatermarkConfig {
    private float alpha = 1.0f;  // Opacity
    private Color color = Color.BLACK;
    private File fontFile;
    private String fontName = "Dialog";  // Default font
    private int fontSize = 12;
    private int fontStyle = Font.PLAIN;
}
```

## 🛠️ Advanced Usage

### Using Preset Watermark Types

```java
public class UsingPresetWatermarkType {

    public static void main(String[] args) {
        byte[] result = EasyWatermark.create()
                .file(fileData)
                .text("Easy-Watermark")
                .easyWatermarkType(EasyWatermarkTypeEnum.CENTER)
                .execute();
    }
  
}
```

### Custom Watermark and Font Configuration

```java
public class CustomWatermarkExample {

    public static void main(String[] args) {
        WatermarkConfig watermarkConfig = new WatermarkConfig();
        watermarkConfig.setAlpha(0.5f);  // Set transparency

        FontConfig fontConfig = new FontConfig();
        fontConfig.setFontSize(30);  // Set font size

        byte[] result = EasyWatermark.create()
                .file(fileData)
                .config(watermarkConfig)
                .config(fontConfig)
                .easyWatermarkType(EasyWatermarkTypeEnum.CENTER)
                .text("Easy-Watermark")
                .execute();
    }
    
}
```

## 📅 Development Plan

- [x] Add watermark to image files
- [x] Add watermark to PDF files
- [x] Add watermark to Word files
- [ ] Improve custom watermark methods
- [ ] Add watermark to Excel files
- [ ] Add watermark to PowerPoint files
- [ ] Hidden watermark

## 🙌 Others

If you have more suggestions, feel free to raise an issue! 😊
