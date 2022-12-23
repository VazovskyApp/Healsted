package app.vazovsky.mypills.extensions

import kotlin.math.roundToInt

/**
 * Форматирование дробных чисел до десятых и без нуля на конце.
 */
fun Float.formatWithoutTrailingZero(): String {
    val temp = "%.1f".format(this)
    if (temp.last() == '0') {
        return temp.substring(0, temp.length - 2)
    }
    return temp.replace(',', '.')
}

/**
 * Форматирование дробных чисел до десятых с округлением по математическим правилам
 */
fun Float.formatDecimal(): String {
    val temp = "%.1f".format((this * 10).roundToInt() / 10f)
    return temp.replace(',', '.')
}
