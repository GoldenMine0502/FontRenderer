package kr.goldenmine.map

import java.awt.Color
import java.awt.Font
import java.io.File

fun main() {
    val fontRoutes = listOf("fonts/Kawashiro_Pump/chifont.ttf")
    val fontSizes = listOf(100f)

    val textRenderer = TextRenderer(
        fontRoutes.zip(fontSizes) { fontRoute, fontSize ->
            Font.createFont(Font.TRUETYPE_FONT, File(fontRoute)).deriveFont(fontSize)
        },
        Color.WHITE,
        shadow = true
    )

    val lines = File("fonts/2020remix/lyrics.txt").readLines()
    for(index in lines.indices step 3) {
        textRenderer.render(lines[index].split(" - ")[1])
    }
    textRenderer.printMap(File("output/array.txt"))
}