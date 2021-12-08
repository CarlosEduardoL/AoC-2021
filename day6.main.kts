import java.nio.file.Files
import java.nio.file.Path

val input = Path.of("input", "input-6.txt")
    .let(Files::readString)
    .split(',')
    .map(String::toInt)
var counter = LongArray(9)
input.forEach { counter[it] += 1L }
repeat(80) {
    counter = LongArray(9){
        when (it) {
            8 -> counter[0]
            6 -> counter[7] + counter[0]
            else -> counter[it+1]
        }
    }
}
println(counter.sum())
repeat(256 - 80) {
    counter = LongArray(9){
        when (it) {
            8 -> counter[0]
            6 -> counter[7] + counter[0]
            else -> counter[it+1]
        }
    }
}
println(counter.sum())


