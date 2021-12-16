package aoc.days

import com.soywiz.korio.file.VfsFile

suspend fun run1(file: VfsFile) {
    val input = file
        .readLines()
        .map { it.toInt() }
// Part 1 -> 1162
    input
        .zipWithNext { a, b -> if (b > a) 1 else 0 }
        .sum()
        .let(::println)

// Part 2 -> 1190
    input
        .windowed(3)
        .map { it.sum() }
        .zipWithNext { a, b -> if (b > a) 1 else 0 }
        .sum()
        .let(::println)
}