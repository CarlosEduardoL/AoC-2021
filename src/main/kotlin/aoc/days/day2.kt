package aoc.days

import com.soywiz.korio.file.VfsFile

data class R2(var aim: Int = 0, var forward: Int = 0, var deep: Int = 0)

enum class Direction {
    forward, down, up
}

data class Movement(val direction: Direction, val amount: Int) {
    constructor(list: List<String>) : this(Direction.valueOf(list[0]), list[1].toInt())
}

suspend fun run2(file: VfsFile){
    val input = file
        .readLines()
        .map { line -> Movement(line.split(' ')) }

    // Part 1 --> 1698735
    val (x,y) = input.fold(0 to 0) { result, movement ->
        when (movement.direction) {
            Direction.forward -> result.first + movement.amount to result.second
            Direction.down -> result.first to result.second + movement.amount
            Direction.up -> result.first to result.second - movement.amount
        }
    }
    println(x * y)

    // Part 2 --> 1594785890
    val (_, forward, deep) = input.fold(R2()) { r2, movement ->
        when(movement.direction){
            Direction.forward -> {
                r2.forward += movement.amount
                r2.deep += movement.amount * r2.aim
            }
            Direction.down -> r2.aim += movement.amount
            Direction.up -> r2.aim -= movement.amount
        }.let {r2}
    }
    println(forward * deep)
}