package aoc.days

import com.soywiz.korio.file.VfsFile

suspend fun run5(file: VfsFile){
    val input = file.readLines()
        .map { it.split(",|->".toRegex()).map(String::trim) }
        .map { row -> row.map { it.toInt() } }
        .map { Line(it) }
        .toList()
    // part 1 - 5576
    println(input.count(true))
    // part 2 - 18144
    println(input.count())
}

fun List<Line>.count(ignoreDiagonal: Boolean = false): Int {
    return mutableMapOf<Point, Int>().also { counter ->
        forEach { line ->
            line.forEach(ignoreDiagonal) {
                counter[it] = 1 + counter.getOrDefault(it, 0)
            }
        }
    }.count { it.value >= 2 }
}

data class Line(val start: Point, val end: Point) {
    constructor(data: List<Int>) : this(Point(data[0], data[1]), Point(data[2], data[3]))

    val isDiagonal: Boolean = start.x != end.x && start.y != end.y

    fun forEach(ignoreDiagonal: Boolean = false, action: (Point) -> Unit) {
        if (isDiagonal && ignoreDiagonal) return
        var actual = start.also(action)
        while (actual != end) {
            actual = actual.stepTo(end).also(action)
        }
    }
}

data class Point(val x: Int, val y: Int) {
    fun stepTo(other: Point): Point {
        val x = if (x == other.x) x else if (x > other.x) x - 1 else x + 1
        val y = if (y == other.y) y else if (y > other.y) y - 1 else y + 1
        return Point(x, y)
    }
}