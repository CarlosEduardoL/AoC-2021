import java.nio.file.Files
import java.nio.file.Path

val input = Path.of("input", "input-1.txt")
    .let(Files::readAllLines)
    .map { it.toInt() }
// Part 1
input
    .zipWithNext { a, b -> if (b > a) 1 else 0 }
    .sum()
    .let(::println)

// Part 2
input
    .windowed(3)
    .map { it.sum() }
    .zipWithNext { a, b -> if (b > a) 1 else 0 }
    .sum()
    .let(::println)