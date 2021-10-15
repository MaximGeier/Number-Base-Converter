package converter

import java.lang.String.format

fun main() {
    while (true) {
        val (sourceBase, targetBase) = when (val s = getString(BASE_PROMPT)) {
            EXIT -> return
            else -> s.split(" ")
        }
        println()

        while (true) {
            val result = when (val s = getString(format(NUMBER_PROMPT, sourceBase, targetBase))) {
                BACK -> break
                else -> convert(s, sourceBase, targetBase)
            }
            println(format(CONVERSION_RESULT, result))
        }
        println()
    }
}