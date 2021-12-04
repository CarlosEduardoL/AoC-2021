import java.nio.file.Paths

fun input(day: Byte) = Paths.get(ClassLoader.getSystemResource("input-$day.txt").toURI())!!
fun test(day: Byte) =  Paths.get(ClassLoader.getSystemResource("test-$day.txt").toURI())!!