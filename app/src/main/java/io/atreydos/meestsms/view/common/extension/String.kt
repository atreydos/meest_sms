package io.atreydos.meestsms.view.common.extension

import java.security.MessageDigest

fun String.toMD5(): String {
    val messageDigest = MessageDigest.getInstance("MD5")
    messageDigest.update(this.toByteArray())
    val arrby = messageDigest.digest()
    val stringBuffer = StringBuffer()
    for (i in arrby.indices) {
        var string3 = Integer.toHexString(255 and arrby[i].toInt())
        while (string3.length < 2) {
            val stringBuilder = StringBuilder()
            stringBuilder.append('0')
            stringBuilder.append(string3)
            string3 = stringBuilder.toString()
        }
        stringBuffer.append(string3)
    }
    return stringBuffer.toString()
}

fun String.extractDigits(): String {
    if (isEmpty()) return ""

    val digitsStringBuilder = StringBuilder()
    for (i in 0 until length) {
        if (Character.isDigit(this[i]))
            digitsStringBuilder.append(this[i])
    }
    return digitsStringBuilder.toString()
}

fun String.formatAsPhoneNumber(): String {
    val digits = this.extractDigits()
    return "+38 (0" + when (digits.length) {
        in 4..5 -> String.format(
            "%s",
            digits.substring(3)
        )
        in 6..8 -> String.format(
            "%s) %s",
            digits.substring(3, 5),
            digits.substring(5)
        )
        in 9..10 -> String.format(
            "%s) %s %s",
            digits.substring(3, 5),
            digits.substring(5, 8),
            digits.substring(8)
        )
        in 11..18 -> String.format(
            "%s) %s %s %s",
            digits.substring(3, 5),
            digits.substring(5, 8),
            digits.substring(8, 10),
            digits.substring(10)
        )
        else -> ""
    }
}