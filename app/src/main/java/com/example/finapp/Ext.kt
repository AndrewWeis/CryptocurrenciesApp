package com.example.finapp

import java.text.SimpleDateFormat
import java.util.*

/**
 * Две внешних функции для форматирования чисел и дат.
 * Float.formatThousands() - используем в списке криптовалют для разделения порядков в числах,
 * Number.dateToString - перевод дат из UNIX в строковый формат, для использования на графике.
 */
fun Float.formatThousands() : String {
    val sb = StringBuilder()
    val formatter = Formatter(sb, Locale.US)
    formatter.format("%(,.0f", this)
    return sb.toString()
}

fun Number.dateToString(pattern: String): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this.toLong()
    return SimpleDateFormat(pattern).format(calendar.time)
}