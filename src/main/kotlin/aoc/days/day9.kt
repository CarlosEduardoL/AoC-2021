package aoc.days

import com.github.ajalt.mordant.TermColors
import com.soywiz.korio.dynamic.KDynamic.Companion.map
import com.soywiz.korio.file.VfsFile

suspend fun run9(file: VfsFile) {
    val input = file.readLines().toList()
    val heightMap =
        Array(input.size) { input[it].toCharArray().map(Char::digitToIntOrNull).filterNotNull().toIntArray() }


    // Part 1 --> 633
    val lowers = buildList {
        heightMap.indices.forEach { i ->
            heightMap[i].indices.forEach { j ->
                val localLowest = heightMap[p(i, j - 1)] > heightMap[i][j] &&
                        heightMap[p(i, j + 1)] > heightMap[i][j] &&
                        heightMap[p(i - 1, j)] > heightMap[i][j] &&
                        heightMap[p(i + 1, j)] > heightMap[i][j]
                if (localLowest) add(Cords(i, j))
            }
        }
    }
    println(lowers.sumOf { heightMap[it] + 1 })

    // Part 2
    val displacement: Array<Cords> = arrayOf(p(y = 1), p(y = -1), p(x = 1), p(x = -1))
    fun countNear(border: MutableList<Cords>): Int {
        val visited = mutableSetOf<Cords>()
        while (border.size > 0) {
            val actual = border.removeFirst().also(visited::add)
            displacement.map { actual + it }.filter { it !in visited && heightMap[it, 9] != 9 }.forEach(border::add)
        }
        return visited.size
    }

    lowers
        .map { countNear(mutableListOf(it)) }
        .sortedByDescending { it }
        .subList(0,3)
        .fold(1, Int::times)
        .let(::println) // --> 1050192

}

data class Cords(val x: Int, val y: Int) {
    operator fun plus(o: Cords) = Cords(x + o.x, y + o.y)
}

fun p(x: Int = 0, y: Int = 0) = Cords(x, y)

operator fun Array<IntArray>.get(point: Cords, default: Int = Int.MAX_VALUE): Int {
    if (point.x !in indices) return default
    if (point.y !in get(0).indices) return default
    return get(point.x)[point.y]
}