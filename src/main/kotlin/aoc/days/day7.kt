package aoc.days

import com.soywiz.korio.file.VfsFile
import kotlin.math.abs

suspend fun run7(file: VfsFile){
    val input = file.readString()
        .split(',')
        .map(String::toInt)
        .toIntArray()
    val min = input.minOf { it }
    val max = input.maxOf { it }
    var sum = Int.MAX_VALUE
    (min..max).forEach { nn ->
        sum = input.sumOf { x -> abs(x - nn) }.takeIf { it < sum } ?: sum
    }
    println(sum) // --> 344297

    sum = Int.MAX_VALUE
    (min..max).forEach { nn ->
        sum = input.sumOf { x -> abs(x - nn).crabEngineering() }.takeIf { it < sum } ?: sum
    }
    println(sum) // --> 97164301
}

fun Int.crabEngineering() = (this downTo 1).sum()