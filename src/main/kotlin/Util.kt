import java.nio.file.Paths
import kotlin.io.path.readLines

fun input(day: Byte) = (Paths.get(ClassLoader.getSystemResource("input-$day.txt").toURI())
    ?: throw Exception("file input-$day.txt is missing in resources"))
    .readLines()

fun test(day: Byte) = (Paths.get(ClassLoader.getSystemResource("test-$day.txt").toURI())
    ?: throw Exception("file test-$day.txt is missing in resources"))
    .readLines()