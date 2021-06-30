package kr.goldenmine.map

import kr.goldenmine.createImage
import java.awt.Color
import java.awt.Font
import java.io.File
import java.util.*

fun main() {
    with(Scanner(System.`in`)) {
        print("폰트 경로: ")
//        val fontRoute = File(nextLine())
        val fontRoute = listOf(
            File("fonts/daddy/JKG-M_3.ttf"),
            File("fonts/daddy/EternalLogo-MVO7Y.ttf")
//            File("fonts/Paddling Blue/Chihaya Jun.ttf"),
//            File("fonts/2020remix/NanumPen.ttf")
        )

        print("가사 경로: ")
//        val lyricsRoute = File(nextLine())
        val lyricsRoute = File("fonts/daddy/lyrics.txt")

        print("색(R,G,B,A): ")
//        val RGBA = nextLine().split(",").map { it.toInt() };
        val RGBA = listOf(0, 0, 0, 255)

        print("배경색(R,G,B,A): ")
//        val RGBABackground = nextLine().split(",").map { it.toInt() };
        val RGBABackground = listOf(255, 255, 255, 255)

//        print("한글자로 분리: ")
//        val oneChar = nextBoolean()

        print("폰트 크기: ")
//        val fontSize = nextFloat()
        val fontSize = listOf(72f, 36f, 36f)

//        print("폰트 스타일: ")
//        val fontStyle = nextInt()

        print("이미지 크기 배율: ")
//        val imageMagnify = nextFloat()
        val imageMagnify = 1f


        // 255 113 181

//        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
//        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, File("A.ttf")))


        val lyrics = lyricsRoute.readLines()
        val lines = fontRoute.size

        for(index in lyrics.indices step lines) {
            repeat(lines) { i->
                val split = lyrics[index + i].split(" - ")
                val txt = if(split.size >= 2) split[1] else lyrics[index + i]
                println("$txt ${fontRoute[i].name}")
                if(txt.isNotEmpty()) {
                    createImage(
                        lyricsRoute.name,
                        Font.createFont(Font.TRUETYPE_FONT, fontRoute[i]).deriveFont(fontSize[i]),
                        imageMagnify,
                        txt,
                        Color(RGBA[0], RGBA[1], RGBA[2], RGBA[3]),
                        Color(RGBABackground[0], RGBABackground[1], RGBABackground[2], RGBABackground[3]), index + i,
                        stroke = true
                    )
                }
            }

        }

//        lyricsRoute.readLines().forEach {
//            val split = it.split(" - ")
//            val txt = if(split.size >= 2) split[1] else it
////            println("$txt ${txt.length > 0}")
//            if(txt.isNotEmpty()) {
//                createImage(
//                    lyricsRoute.name,
//                    Font.createFont(Font.TRUETYPE_FONT, fontRoute).deriveFont(fontSize),
//                    imageMagnify,
//                    txt,
//                    Color(RGBA[0], RGBA[1], RGBA[2], RGBA[3]),
//                    Color(RGBABackground[0], RGBABackground[1], RGBABackground[2], RGBABackground[3]), index
//                )
//            }
//            index++
//        }
    }
}