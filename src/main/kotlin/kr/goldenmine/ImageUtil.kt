package kr.goldenmine

import java.awt.*
import java.awt.font.FontRenderContext
import java.awt.font.TextLayout
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO



fun createImage(
    title: String,
    font: Font,
    magnify: Float,
    txt: String,
    color: Color,
    backgroundColor: Color,
    index: Int,
    keepName: Boolean = false,
    stroke: Boolean = false,
    shadow: Boolean = false,
    rect: Boolean = false
) {
    if(txt == "") {
        throw RuntimeException("txt cant be empty")
    }

    val realMapperNames = HashMap<String, String>()
    realMapperNames["Mapped by wolf"] = "Mapped by wolf3211"
    realMapperNames["Mapped by hazu"] = "Mapped by Hazu-"
//    realMapperNames["Mapped by Real"] = "Mapped by Realazy"
//    realMapperNames["Mapped by AF"] = "Mapped by Affirmation"
//    realMapperNames["Mapped by Acyl"] = "Mapped by Acylica"
//    realMapperNames["Mapped by Frontier"] = "Mapped by  - Frontier -"
//    realMapperNames["Mapped by 1112"] = "Mapped by Otosaka-Yu"
//    realMapperNames["Mapped by KittyAdventure"] = "Mapped by gokugohan12468"
//    realMapperNames["Mapped by Firis"] = "Mapped by -Atri-"
//    realMapperNames["Mapped by yf"] = "Mapped by yf_bmp"
//    realMapperNames["Mapped by Rose"] = "Mapped by Rose Pacifica"
//    realMapperNames["Mapped by Alphabet"] = "Mapped by Aistre"
//    realMapperNames["Mapped by Asa"] = "Mapped by Kazato Asa"

    val mapperTxt = if(realMapperNames.containsKey(txt)) realMapperNames[txt]!! else txt
//    println(txt + ", " + realMapperNames.containsKey(txt))
    val affineTransform = AffineTransform()
    val frc = FontRenderContext(affineTransform, true, true)
    val bounds = font.getStringBounds(mapperTxt, frc)

    val textTl = TextLayout(mapperTxt, font, frc)
//    textTl.

    val outline = textTl.getOutline(null)

    val rh = RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    rh.add(RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON))



    val img = BufferedImage(
        (bounds.width * magnify).toInt() + 20,
        (bounds.height * 1.25 * magnify).toInt(),
        BufferedImage.TYPE_INT_ARGB
    )

    val graphics = img.graphics as Graphics2D
    val originalTransform = graphics.transform

    val newTransform = AffineTransform()
    newTransform.translate(10.0, (bounds.height * magnify * 0.9))

    graphics.setRenderingHints(rh)
//    graphics.color = backgroundColor

//    graphics.fillRect(0, 0, img.width, img.height);
//    graphics.color = color
    graphics.font = font
//    graphics.drawString(mapperTxt, 10, (bounds.height * magnify * 0.95).toInt())
//    BasicStroke()
//    graphics.stroke = BasicStroke(5F)

    graphics.transform = newTransform
    graphics.color = backgroundColor
    if(stroke) {
        graphics.stroke = BasicStroke(5.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND)
        graphics.draw(outline)
    }
    if(rect) {
        graphics.fillRoundRect(0, 0, img.width, img.height, font.size / 4, font.size / 4)
    }
    if(shadow) {
        val shadowTransform = AffineTransform()
        shadowTransform.translate(10.0 + 1.5, (bounds.height * magnify * 0.9) + 1.5)
        graphics.transform = shadowTransform
        graphics.color = Color(150, 150, 150)
        graphics.fill(outline)


        graphics.transform = newTransform
    }
    graphics.color = color
    graphics.fill(outline)

    graphics.transform = originalTransform

//    drawFancyString(graphics, mapperTxt, font, 10, 0, 5F, color, Color.WHITE)
//    graphics.draw(outline);

    val resultFolderName = "results$title"

    val folder = File(resultFolderName)
    folder.mkdirs()

    ImageIO.write(img, "png", File("$resultFolderName/${if (keepName) txt else index.toString()}.png"))
}

//
//fun drawFancyString(g: Graphics2D, str: String, font: Font, x: Int, y: Int, stroke: Float, color: Color, strokeColor: Color) {
//    if (str.length == 0) return
//    val original = g.transform
//    val tl = TextLayout(str, font, g.fontRenderContext)
//    val transform = g.transform
//    val fm = g.getFontMetrics(font)
//    val outline = tl.getOutline(null)
////    val bound = outline.bounds
//    transform.translate(x.toDouble(), (y + fm.ascent).toDouble())
//
//    g.transform = transform
//    g.color = color
//    g.fill(outline)
//
//    g.stroke = BasicStroke(stroke)
//    g.color = strokeColor
//    g.draw(outline)
//
//    g.transform = original
//}