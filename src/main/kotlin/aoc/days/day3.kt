package aoc.days

import aoc.days.BitCounter.Companion.value
import com.soywiz.korio.file.VfsFile
import kotlin.math.abs

class Number private constructor(private val entries: Array<BitCounter>) {
    constructor(binary: String): this(Array(binary.length) { binary[it].value })

    val gamma: Int get() = entries.map { it.frequentDigit }.joinToString(separator = "").toInt(2)
    val epsilon: Int get() = entries.map { it.infrequentDigit }.joinToString(separator = "").toInt(2)

    operator fun plus(other: Number) = Number(Array(entries.size){ entries[it] + other.entries[it] })
    operator fun get(index: Int, lestCommon: Boolean = false) = entries[index].let { if (lestCommon) it.infrequentDigit else it.frequentDigit }

    override fun toString(): String = entries.map { it.frequentDigit }.joinToString(separator = "")
}

class BitCounter private constructor(private val zeros: Int, private val ones: Int){
    val frequentDigit get() = if (zeros > ones) 0 else 1
    val infrequentDigit get() = abs(frequentDigit - 1)

    operator fun plus(other: BitCounter) = BitCounter(zeros + other.zeros, ones + other.ones)

    companion object {
        val Char.value get() = if (this == '0') BitCounter(1,0) else BitCounter(0,1)
    }
}

fun List<Number>.sum(): Number {
    var result: Number? = null
    forEach {
        result = result?.plus(it) ?: it
    }
    return result!!
}

suspend fun run3(file: VfsFile) {
    val input = file
        .readLines()
        .map { Number(it) }
        .toList()
    val result: Number = input.reduce { acc, element -> acc + element }
    println(result.gamma * result.epsilon) //4139586

    var rest = input
    var index = 0
    while (rest.size > 1){
        rest = rest.filter { it[index] == rest.sum()[index] }
        index++
    }
    val oxygen = rest.first().gamma
    rest = input
    index = 0
    while (rest.size > 1){
        val lessCommon = rest.sum()[index, true]
        rest = rest.filter { it[index] == lessCommon }
        index++
    }
    val c02 = rest.first().gamma
    println(oxygen * c02) //1800151

}