package converter

import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

fun convert(s: String, sourceBase: String, targetBase: String): String {
    var result = ""
    if (!s.contains(".")) {
        result = s.toDecimal(sourceBase).toRadixString(targetBase)
    } else if (s.endsWith(".0")) {
        result = "${s.substringBefore(".").toDecimal(sourceBase).toRadixString(targetBase)}.00000"
    } else result = s.substringBefore(".").toDecimal(sourceBase).toRadixString(targetBase) +
            ".${s.substringAfter(".").fractionalToDecimal(sourceBase).fractionalToRadixString(targetBase)}"
    return result
}

fun BigInteger.toRadixString(targetBase: String): String {
    val base = BigInteger(targetBase)
    var q = this
    var result = ""
    do {
        result = "${remainderToChar((q % base).toInt())}$result"
        q /= base
    } while (q > BigInteger.ZERO)

    return result
}

fun String.toDecimal(sourceBase: String): BigInteger {
    val base = BigInteger(sourceBase)
    var result = BigInteger.ZERO

    for (i in indices) {
        val digit = BigInteger(charToDecimal(get(i)))
        result += digit * base.pow(lastIndex - i)
    }
    return result
}

fun String.fractionalToDecimal(sourceBase: String): BigDecimal {
    val base = BigDecimal(sourceBase)
    var result = BigDecimal.ZERO

    for (i in indices) {
        val digit = BigInteger(charToDecimal(get(i)))
        result += BigDecimal(digit) * base.pow(-(indices.indexOf(i) + 1), MathContext.DECIMAL64)
    }
    return result.setScale(5, RoundingMode.CEILING)
}

fun BigDecimal.fractionalToRadixString(targetBase: String): String {
    val base = BigDecimal(targetBase)
    var q = this
    var result = ""

    for (i in 1..5) {
        result = "$result${remainderToChar((q * base).toInt())}"
        q = (q * base).rem(BigDecimal(1))
    }
    return result
}