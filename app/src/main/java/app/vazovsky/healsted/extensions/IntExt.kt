package app.vazovsky.healsted.extensions

import androidx.annotation.ColorInt
import app.vazovsky.healsted.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Форматирует число в строку с разделением пробелами по три знака
 * Пример: ""12 000"
 */
fun Int.formatDecimal(): String {
    val formatter = NumberFormat.getInstance(Locale("ru", "RU")) as DecimalFormat
    return formatter.format(this)
}

fun @receiver:ColorInt Int.getColorIdFromPosition(): Int = when (this % 5) {
    0 -> R.color.pillsCardBlue
    1 -> R.color.pillsCardOrange
    2 -> R.color.pillsCardRed
    3 -> R.color.pillsCardViolet
    else -> R.color.pillsCardGreen
}

