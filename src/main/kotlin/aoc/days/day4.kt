package aoc.days

import aoc.debug
import com.soywiz.korio.file.VfsFile
import kotlin.system.exitProcess

suspend fun run4(file: VfsFile){
    val input = file
        .readLines()
        .map { " {2,}".toRegex().replace(it, " ").trim() }
        .filter { it != "" }
        .toMutableList()
    val numbers = input.removeAt(0).split(',').map(String::toByte).toByteArray()
    val boards = input.windowed(5, step = 5).map { Board(it) }
    val (winner, lastNumber) = getWinner(numbers, boards)
    println(winner.score * lastNumber)
    val (looser, lastNumber2) = getLooser(numbers, boards)
    println(looser.score * lastNumber2)

}

class Piece(var isMarked: Boolean, val value: Byte) {
    override fun toString() = if (isMarked) "$ANSI_GREEN$value$ANSI_RESET" else "$value"

    companion object{
        const val ANSI_GREEN = "\u001B[32m"
        const val ANSI_RESET = "\u001B[0m"
    }
}

class Board(private val inner: Array<Array<Piece>>) {

    constructor(input: List<String>) : this(Array(5) {
        input[it].split(' ').map(String::toByte).map { n -> Piece(false, n) }.toTypedArray()
    })

    private fun get(i: Int, j: Int, value: Byte = -1) =
        inner[i][j].also { it.isMarked = it.isMarked || value == it.value }

    fun markAndCheck(value: Byte): Boolean {
        for (i in 0 until 5) {
            if ((0 until 5).all { get(i, it, value).isMarked }) return true
            if ((0 until 5).all { get(it, i, value).isMarked }) return true
        }
        inner.forEach { row -> row.forEach { it.isMarked = it.isMarked || it.value == value } }
        return false
    }
    val score get() = inner.sumOf { row -> row.sumOf { if (!it.isMarked) it.value.toInt() else 0 } }
    override fun toString(): String {
        return inner.joinToString("\n") { it.joinToString(" ") }
    }
}

fun getWinner(numbers: ByteArray, boards: List<Board>): Pair<Board, Byte> {
    numbers.forEach { number ->
        debug { "--------------$number--------------------" }
        boards.forEach { board ->
            if (board.markAndCheck(number)) return board.also(::debug) to number
            debug { board }
            debug()
        }
    }
    exitProcess(255)
}

fun getLooser(numbers: ByteArray, boards: List<Board>): Pair<Board, Byte> {
    var boards = boards
    numbers.forEach { number ->
        debug { "--------------$number--------------------" }
        boards = boards.filter { board ->
            !board.also(::debug).also { debug() }.markAndCheck(number).also {
                if (boards.size == 1 && it) return boards.first() to number
            }
        }
    }
    exitProcess(255)
}