package aoc.days

import com.soywiz.korio.file.VfsFile

suspend fun run6(file: VfsFile) {
    val input = file.readString()
        .split(',')
        .map(String::toInt)
    var counter = LongArray(9)
    input.forEach { counter[it] += 1L }
    repeat(80) {
        counter = LongArray(9) {
            when (it) {
                8 -> counter[0]
                6 -> counter[7] + counter[0]
                else -> counter[it + 1]
            }
        }
    }
    println(counter.sum())
    repeat(256 - 80) {
        counter = LongArray(9) {
            when (it) {
                8 -> counter[0]
                6 -> counter[7] + counter[0]
                else -> counter[it + 1]
            }
        }
    }
    println(counter.sum())
}