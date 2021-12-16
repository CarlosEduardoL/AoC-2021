package aoc

import aoc.days.*
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.soywiz.korio.file.fullNameWithoutExtension
import com.soywiz.korio.file.std.resourcesVfs
import kotlinx.coroutines.runBlocking

var isDebug: Boolean = false

class AOC : CliktCommand() {

    private val test by option("-t", help = "Use test input").flag(default = false)
    private val debug by option("-d", help = "Use test input").flag(default = false)
    private val day by argument().int()

    override fun run() = runBlocking<Unit> {
        isDebug = debug
        val input = (if (test) "test-$day.txt" else "input-$day.txt")
            .let(resourcesVfs::get)
        debug { "loading ${input.fullNameWithoutExtension}" }
        when (day) {
            1 -> run1(input)
            2 -> run2(input)
            3 -> run3(input)
            4 -> run4(input)
            5 -> run5(input)
            6 -> run6(input)
            7 -> run7(input)
            8 -> run8(input)
            9 -> run9(input)
            10 -> run10(input)
            else -> println("TODO yet")
        }
    }
}

inline fun debug(jumpLine: Boolean = true, message: () -> Any?) {
    if (isDebug) print("${message()}${if (jumpLine) "\n" else ""}")
}

fun debug(message: Any? = "", jumpLine: Boolean = true) = debug(jumpLine) { message }

fun main(args: Array<String>) = AOC().main(args)
