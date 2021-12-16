package aoc.days

import com.soywiz.korio.file.VfsFile

suspend fun run8(file: VfsFile) {
    val input = file.readLines().toList()
    // Part 1
    input.asSequence()
        .map { it.split('|')[1].trim() }
        .map { it.split(' ') }
        .flatten()
        .map { it.length }
        .count { it == 2 || it == 4 || it == 3 || it == 7 }
        .let(::println) // --> 367
    // Part 2
    // -- https://stackoverflow.com/questions/60811453/creating-a-method-that-generates-all-permutations-of-string-in-kotlin-using-recu --
    fun String.permute(result: String = ""): List<String> =
        if (isEmpty()) listOf(result) else flatMapIndexed { i, c -> removeRange(i, i + 1).permute(result + c) }
    //-----------------------------------------------------------

    val possibilities = "abcdefg"
        .permute()
        .map { option ->
            option.mapIndexed { index, c ->
                c to (index + 'a'.code).toChar()
            }.toMap()
        }

    fun String.original(possibility: Map<Char, Char>) =
        map { possibility[it] }.sortedBy { it }.joinToString(separator = "")

    val expected = listOf("abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg")
    input
        .asSequence()
        .map { it.split('|')[0].trim() }
        .map { it.split(' ') }
        .map { possibilities.first { possibility -> it.map { it.original(possibility) }.toSet() == expected.toSet() } }
        .mapIndexed { index, possibility ->
            input[index]
                .split('|')[1].trim()
                .split(' ')
                .map { expected.indexOf(it.original(possibility)) }
                .joinToString(separator = "")
                .toInt()
        }
        .sum()
        .let(::println) // --> 974512
}