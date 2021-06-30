package kr.goldenmine.map

import java.awt.*
import java.awt.font.FontRenderContext
import java.awt.font.TextLayout
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class TextRenderer(
    private val fonts: List<Font>,
    private val color: Color,
    private val stroke: Boolean = false,
    private val rect: Boolean = false,
    private val rectColor: Color = Color(255, 255, 255, 80),
    private val shadow: Boolean = false,
    private val shadowColor: Color = Color(150, 150, 150)
) {
    private val map = ArrayList<String>()

    init {
        File("output").mkdirs()
    }

    fun render(text: String, line: Int = 0) {
        text.split("").forEach {
            if(it.isNotEmpty()) {
                if (!map.contains(it)) {
                    val image = getImage(fonts[line], it)

                    val outputFile = File("output/${map.size}.png")
                    if(!outputFile.exists()) outputFile.createNewFile()

                    ImageIO.write(image, "png", outputFile)

                    map.add(it)
                }
            }
        }
    }

    fun printMap(file: File, lineSplit: Int = 20) {
        if(!file.exists()) file.createNewFile()

        val builder = StringBuilder()
        builder.append("string[] arr = ")
        builder.append("{")

        var count = 0
        map.forEach {
            builder.append("\"")
            builder.append(it)
            builder.append("\",")
            count++
            if(count >= lineSplit) {
                builder.append("\n")
                count = 0
            }
        }
        builder.append("};")

        file.writeText(builder.toString())
    }

    fun renderAll(lyricsRoute: File, lines: Int = 1) {
        val lyrics = lyricsRoute.readLines()

        for(index in lyrics.indices step lines) {
            for(i in 0 until lines) {
                val split = lyrics[index + i].split(" - ")
                val txt = if(split.size >= 2) split[1] else lyrics[index + i]

                render(txt, i)
            }

        }
    }

    fun getImage(font: Font, text: String): BufferedImage {
        val affineTransform = AffineTransform()
        val frc = FontRenderContext(affineTransform, true, true)
        val bounds = font.getStringBounds(text, frc)

        val textTl = TextLayout(text, font, frc)
//    textTl.

        val outline = textTl.getOutline(null)

        val rh = RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
        rh.add(RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON))

        val img = BufferedImage(
            (bounds.width).toInt() + 20,
            (bounds.height * 1.25).toInt(),
            BufferedImage.TYPE_INT_ARGB
        )

        val graphics = img.graphics as Graphics2D
        val originalTransform = graphics.transform

        val newTransform = AffineTransform()
        newTransform.translate(10.0, (bounds.height * 0.9))

        graphics.setRenderingHints(rh)


        graphics.font = font


        graphics.transform = newTransform
        graphics.color = color
        if(stroke) {
            graphics.stroke = BasicStroke(5.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND)
            graphics.draw(outline)
        }
        if(rect) {
            graphics.color = rectColor
            graphics.fillRoundRect(0, 0, img.width, img.height, font.size / 4, font.size / 4)
        }
        if(shadow) {
            val shadowTransform = AffineTransform()
            shadowTransform.translate(10.0 + 2, (bounds.height * 0.9) + 2)
            graphics.transform = shadowTransform
            graphics.color = shadowColor
            graphics.fill(outline)

            graphics.transform = newTransform
        }
        graphics.color = color
        graphics.fill(outline)

        graphics.transform = originalTransform

        return img
    }
}