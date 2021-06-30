package kr.goldenmine

import java.io.File
import java.util.*

fun main() {
    with(Scanner(System.`in`)) {
//        print("route: ");
//        val route = File(nextLine())
        val route = File("fonts/daddy/mapper.txt")

        route.listFiles()?.forEach {
            if(it.name.endsWith("osu")) {
                val split = it.name.split("[")[1].split("'");
                val result = if (split.size >= 2) split[0] else "Kawashiro";

                println("Mapped by $result")
            }
        }
    }

    //Kawashiro - Asa -> Kazato Asa
}