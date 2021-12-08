import java.nio.file.Files
import java.nio.file.Path

val input = Path.of("input", "input-2.txt")
    .let(Files::readAllLines)
    .map { line -> Movement(line.split(' ')) }

val (x,y) = input.fold(0 to 0) { result, movement ->
    when (movement.direction) {
        Direction.forward -> result.first + movement.amount to result.second
        Direction.down -> result.first to result.second + movement.amount
        Direction.up -> result.first to result.second - movement.amount
    }
}
println(x * y)

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

data class R2(var aim: Int = 0, var forward: Int = 0, var deep: Int = 0)

enum class Direction {
    forward, down, up
}

data class Movement(val direction: Direction, val amount: Int) {
    constructor(list: List<String>) : this(Direction.valueOf(list[0]), list[1].toInt())
}