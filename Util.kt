package converter

fun remainderToChar(rem: Int) = if (rem < 10) '0' + rem else 'a' + rem - 10

fun charToDecimal(c: Char) = if (c in '0'..'9') "$c" else "${c.code - 'a'.code + 10}"

fun getString(prompt: String): String {
    print(prompt)
    return readLine()!!
}