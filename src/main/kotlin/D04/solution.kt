package D04

import input
import test
import kotlin.io.path.readLines

fun main() {
    val input =
        input(4).readLines().map { " {2,}".toRegex().replace(it, " ").trim() }.filter { it != "" }.toMutableList()
    val numbers = input.removeAt(0).split(',').map(String::toByte).toByteArray()
    val boards = input.windowed(5, step = 5).map{Board(it)}
    val (winner, lastNumber) = getWinner(numbers, boards)
    println(winner.score * lastNumber)
    val (looser, lastNumber2) = getLooser(numbers, boards)
    println(looser.score * lastNumber2)
}

fun getWinner(numbers: ByteArray, boards: List<Board>): Pair<Board, Byte>{
    numbers.forEach{ number ->
        println("--------------$number--------------------")
        boards.forEach { board ->
            if (board.markAndCheck(number)) return board.also(::println) to number
            println(board)
            println()
        }
    }
    throw Exception("This will never happened")
}

fun getLooser(numbers: ByteArray, boards: List<Board>): Pair<Board, Byte>{
    var boards = boards
    numbers.forEach{ number ->
        println("--------------$number--------------------")
        boards = boards.filter { board ->
            !board.also(::println).also { println() }.markAndCheck(number).also {
                if (boards.size == 1 && it) return boards.first() to number
            }
        }
    }
    throw Exception("This will never happened")
}

@JvmInline
value class Board(private val inner: Array<Array<Number>>) {
    constructor(input: List<String>) : this(Array(5) {
        input[it].split(' ').map(String::toByte).map { n -> Number(false, n) }.toTypedArray()
    })
    private fun get(i: Int, j:Int, value: Byte = -1) = inner[i][j].also { it.isMarked = it.isMarked || value == it.value }
    fun markAndCheck(value: Byte): Boolean {
        for (i in 0 until 5) {
            if ((0 until 5).all { get(i,it,value).isMarked }) return true
            if ((0 until 5).all { get(it,i,value).isMarked }) return true
        }
        inner.forEach { row -> row.forEach { it.isMarked = it.isMarked || it.value == value } }
        return false
    }
    val score get() = inner.sumOf { row -> row.sumOf { if (!it.isMarked) it.value.toInt() else 0 } }
    override fun toString(): String {
        return inner.joinToString("\n") { it.joinToString(" ") }
    }
}

class Number(var isMarked: Boolean, val value: Byte) {
    override fun toString() = if (isMarked) "$ANSI_GREEN$value$ANSI_RESET" else "$value"
}

const val ANSI_GREEN = "\u001B[32m"
const val ANSI_RESET = "\u001B[0m"