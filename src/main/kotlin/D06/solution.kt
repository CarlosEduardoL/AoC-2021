package D06

import input
import test

fun main() {
    val input = input(6).first().split(',').map(String::toInt)
    var counter = LongArray(9)
    input.forEach { counter[it] += 1L }
    repeat(80) { day ->
        counter = LongArray(9){
            when (it) {
                8 -> counter[0]
                6 -> counter[7] + counter[0]
                else -> counter[it+1]
            }
        }
    }
    println(counter.sum())
    repeat(256 - 80) { day ->
        counter = LongArray(9){
            when (it) {
                8 -> counter[0]
                6 -> counter[7] + counter[0]
                else -> counter[it+1]
            }
        }
    }
    println(counter.sum())
}

