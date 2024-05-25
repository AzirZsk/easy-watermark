# EasyWatermark

[图片水印样例](doc/ImageWatermarkExample.md)
[Pdf水印样例](doc/PdfWatermarkExample.md)

EasyWatermark是一个简单易用的水印添加框架，框架抽象了各个文件类型的对于水印添加的方法。仅使用几行代码即可为不同类型的文件添加相同样式的水印。

# 快速开始

## 1. 添加依赖

```xml
<dependency>
  <groupId>org.easywatermark</groupId>
  <artifactId>easy-watermark</artifactId>
  <version>最新版本</version>
</dependency>
```

## 2. 添加水印
```java
byte[] result = EasyWatermark.create()
        .file(fileData)
        .text("Easy-Watermark")
        .execute();
```
# 更多配置
## 预制水印类型

- **CUSTOM**：自定义水印，使用框架抽象的统一方法，对传入文件的每个页面进行特殊处理（开发中）
- **CENTER**：居中，在页面的各个居中位置上添加。
  - **VERTICAL_CENTER**：水印垂直居中
  - **LEFT_CENTER**：靠左居中
  - **RIGHT_CENTER**：靠右居中
  - **TOP_CENTER**：顶部居中
  - **BOTTOM_CENTER**：底部居中
- **OVERSPREAD**：页面铺满，可设置倾斜角度
  - **LOW**：低铺满，水印内容占比33%
  - **NORMAL**：一般铺满，水印内容占比66%
  - **HIGH**：高铺满，水印内容占比90%
- **DIAGONAL**：页面对角水印
  - **TOP_TO_BOTTOM**：文字从上到下
  - **BOTTOM_TO_TOP**：文字从下到上

## 水印配置
对应`WatermarkConfig`类，该类会对水印的各项属性进行设置。

```java
public class WatermarkConfig {
  // 水印颜色
  private Color color = Color.BLACK;
  // 忽略旋转，仍在开发
  private boolean ignoreRotation = true;
  // 水印透明度
  private float alpha = 1;
  // 铺满水印子类型
  private OverspreadTypeEnum overspreadType = OverspreadTypeEnum.NORMAL;
  // 居中水印子类型
  private CenterLocationTypeEnum centerLocationType = CenterLocationTypeEnum.VERTICAL_CENTER;
  // 对角水印子类型
  private DiagonalDirectionTypeEnum diagonalDirectionType = DiagonalDirectionTypeEnum.TOP_TO_BOTTOM;

  /**
   * An angle, in degrees
   * The angle of clockwise rotation
   */
  private float angle = 0;
}
```
## 字体配置
对应`FontConfig`类，该类会对水印文字的各项属性进行设置。

```java
public class FontConfig {
  // 字体颜色
  private Color color = Color.BLACK;
  // 字体文件
  private File fontFile;

  /**
   * 默认的字体，目前仅在图片水印中生效
   * Default font name is Dialog
   */
  private String fontName = "Dialog";
  // 字体大小
  private int fontSize = 12;

  /**
   * 字体样式：加粗、斜体、正常
   * @see Font#PLAIN
   * @see Font#BOLD
   * @see Font#ITALIC
   */
  private int fontStyle = Font.PLAIN;
}
```
# 进阶用法
## 使用预设水印类型
```java
byte[] result = EasyWatermark.create()
        .file(fileData)
        .text("Easy-Watermark")
        // 选择不同的水印类型
        .easyWatermarkType(EasyWatermarkTypeEnum.CENTER)
        .execute();
```
## 自定义水印配置以及字体配置

```java
WatermarkConfig watermarkConfig = new WatermarkConfig();
// 设置水印透明度
watermarkConfig.setAlpha(0.5f);

FontConfig fontConfig = new FontConfig();
// 设置水印文字大小
fontConfig.setFontSize(30);

byte[] result = EasyWatermark.create()
        .file(fileData)
        // 自定义配置
        .config(watermarkConfig)
        .config(fontConfig)
        // 水印类型
        .easyWatermarkType(EasyWatermarkTypeEnum.CENTER)
        .text("Easy-Watermark")
        .executor();
```

# 开发计划

- [x] 图片类型文件添加水印
- [x] PDF类型文件添加水印
- [x] 用户自定义水印方法完善
- [ ] Word文件添加水印
- [ ] Excel文件添加水印
- [ ] PowerPoint文件添加水印
- [ ] 隐藏水印

# 其他

如有更多建议，欢迎提出issue😊
