package D01

import input
import kotlin.io.path.readLines

fun main() {

    val input = input(1).readLines().map { it.toInt() }
    val result = input.zipWithNext { a, b -> if (b > a) 1 else 0 }.sum()
    println(result)

    val windowed = input.windowed(3).map { it.sum() }
    val result2 = windowed.zipWithNext { a, b -> if (b > a) 1 else 0 }.sum()
    println(result2)

}