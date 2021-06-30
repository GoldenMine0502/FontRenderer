package kr.goldenmine

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val file = File("C:\\Users\\Taewon\\Downloads\\cursortrail.png")
    val image = ImageIO.read(file);
    val newImage = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_ARGB_PRE)
    repeat(image.width) { x ->
        repeat(image.height) { y ->
            val color = Color(image.getRGB(x, y), true)
            val newColor = Color(color.red, color.green, color.blue, color.alpha / 8)


            newImage.setRGB(x, y, newColor.rgb)
        }
    }
    ImageIO.write(newImage, "png", File("C:\\Users\\Taewon\\Downloads\\cursortrail2.png"))
}