package app.vazovsky.healsted.extensions

import java.util.*

/** Делает первый символ строки с заглавной буквы */
fun String.capitalizeFirstChar(locale: Locale): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
}
