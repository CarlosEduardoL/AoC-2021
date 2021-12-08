import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.abs

val input = Path.of("input", "input-7.txt")
    .let(Files::readString)
    .split(',')
    .map(String::toInt)
    .toIntArray()
val min = input.minOf { it }
val max = input.maxOf { it }
var sum = Int.MAX_VALUE
(min..max).forEach { nn ->
    sum = input.sumOf { x -> abs(x - nn) }.takeIf { it < sum } ?: sum
}
println(sum)

sum = Int.MAX_VALUE
(min..max).forEach { nn ->
    sum = input.sumOf { x -> abs(x - nn).crabEngineering() }.takeIf { it < sum } ?: sum
}
println(sum)

fun Int.crabEngineering() = (this downTo 1).sum()