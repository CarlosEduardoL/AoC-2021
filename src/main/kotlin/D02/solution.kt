package D02

import input

fun main() {
    val input = input(2).map { line -> Movement(line.split(' ')) }
    val result = input.fold(0 to 0) { result, movement ->
        when (movement.direction) {
            Direction.forward -> result.first + movement.amount to result.second
            Direction.down -> result.first to result.second + movement.amount
            Direction.up -> result.first to result.second - movement.amount
        }
    }
    println(result.first * result.second)
    val result2 = input.fold(R2()) { r2, movement ->
        when(movement.direction){
            Direction.forward -> {
                r2.x += movement.amount
                r2.y += movement.amount * r2.aim
            }
            Direction.down -> r2.aim += movement.amount
            Direction.up -> r2.aim -= movement.amount
        }.let {r2}
    }
    println(result2.x * result2.y)
}

@JvmInline
value class R2 private constructor(private val inner: IntArray) {
    constructor() : this(IntArray(3))

    var aim: Int
        get() = inner[0]
        set(value) {
            inner[0] = value
        }
    var x: Int
        get() = inner[1]
        set(value) {
            inner[1] = value
        }
    var y: Int
        get() = inner[2]
        set(value) {
            inner[2] = value
        }
}

enum class Direction {
    forward, down, up
}

@JvmInline
value class Movement(private val inner: Pair<Direction, Int>) {
    constructor(list: List<String>) : this(Direction.valueOf(list[0]) to list[1].toInt())

    val direction: Direction get() = inner.first
    val amount: Int get() = inner.second
}