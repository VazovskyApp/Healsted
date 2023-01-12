package app.vazovsky.healsted.extensions

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
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
 * Форматирование дробных чисел до десятых и без нуля на конце.
 */
fun Float.formatDecimalWithSpacing(): String {
    val df = DecimalFormat(
        "###,###.#",
        DecimalFormatSymbols(Locale.getDefault()).apply {
            decimalSeparator = '.'
            groupingSeparator = ' '
            patternSeparator = ' '
        }
    )
    df.roundingMode = RoundingMode.CEILING
    return df.format(this)
}


/**
 * Форматирование дробных чисел до десятых с округлением по математическим правилам
 */
fun Float.formatDecimal(): String {
    val temp = "%.1f".format((this * 10).roundToInt() / 10f)
    return temp.replace(',', '.')
}
