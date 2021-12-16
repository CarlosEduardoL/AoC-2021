package aoc.days

import aoc.debug
import com.soywiz.korio.file.VfsFile
import java.util.*

suspend fun run10(file: VfsFile) {
    val input = file
        .readLines()
        .toList()

    input
        .map { line ->
            val stack: Stack<Char> = Stack()
            line.forEach {
                if (it in open) stack.push(it)
                else if (stack.pop() to it !in pairs) return@map it.value
            }
            0
        }
        .sum()
        .let(::println)

    val scores = input
        .mapNotNull { line ->
            val stack: Stack<Char> = Stack()
            line.forEach {
                if (it in open) stack.push(it)
                else if (stack.pop() to it !in pairs) return@mapNotNull null
            }
            stack.takeIf { it.isNotEmpty() }?.complete()
        }
        .also(::debug)
        .map { completion ->
            completion.fold(0L) { total, actual ->
                (total * 5) + actual.point
            }
        }
        .also(::debug)
    scores
        .sorted()[scores.size/2]
        .let(::println)
}

fun Stack<Char>.complete(): String = StringBuilder().apply {
    while (this@complete.isNotEmpty()) {
        append(
            when (pop()) {
                '(' -> ')'
                '[' -> ']'
                '{' -> '}'
                '<' -> '>'
                else -> throw Exception("This will never happen")
            }
        )
    }
}.toString()

val Char.point
    get() = when (this) {
        ')' -> 1
        ']' -> 2
        '}' -> 3
        '>' -> 4
        else -> 0
    }

val Char.value
    get() = when (this) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> 0
    }

val pairs = setOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>'
)

val open = pairs.map(Pair<*, *>::first).toSet()