package D03

import D03.Representativity.Companion.value
import input
import kotlin.io.path.readLines
import kotlin.math.abs

fun main() {
    val input = input(3).readLines()
    var result: Number? = null
    input.map { Number(it) }.forEach {
        result = result?.plus(it) ?: it
    }
    println(result!!.gamma * result!!.epsilon)

    var rest = input.map { Number(it) }
    var index = 0
    while (rest.size > 1){
        rest = rest.filter { it[index] == rest.sum()[index] }
        index++
    }
    val oxygen = rest.first().gamma
    rest = input.map { Number(it) }
    index = 0
    while (rest.size > 1){
        val lessCommon = rest.sum()[index, true]
        rest = rest.filter { it[index] == lessCommon }
        index++
    }
    val c02 = rest.first().gamma
    println(oxygen * c02)
}

fun List<Number>.sum(): Number {
    var result: Number? = null
    forEach {
        result = result?.plus(it) ?: it
    }
    return result!!
}

@JvmInline
value class Number private constructor(private val entries: Array<Representativity>) {
    constructor(binary: String): this(Array(binary.length) { binary[it].value })
    operator fun plus(other: Number) = Number(Array(entries.size){ entries[it] + other.entries[it] })
    operator fun get(index: Int, lestCommon: Boolean = false): Byte = entries[index].digit.let { if (lestCommon) abs(it-1).toByte() else it }
    val gamma: Int get() = entries.map { it.digit }.joinToString(separator = "").toInt(2)
    val epsilon: Int get() = entries.map { abs(it.digit - 1) }.joinToString(separator = "").toInt(2)
    override fun toString(): String = entries.map { it.digit }.joinToString(separator = "")
}
@JvmInline
value class Representativity private constructor(private val inner: IntArray){
    val digit: Byte get() = if (inner[0] > inner[1]) 0 else 1
    companion object {
        val Char.value get() = Representativity(if (this == '0') intArrayOf(1,0) else intArrayOf(0,1))
    }
    operator fun plus(other: Representativity) = Representativity(intArrayOf(inner[0] + other.inner[0], inner[1] + other.inner[1]))
}
