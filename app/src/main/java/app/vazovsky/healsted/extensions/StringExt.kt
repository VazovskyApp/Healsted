package app.vazovsky.healsted.extensions

import java.util.*

fun String.capitalizeFirstChar(locale: Locale): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
}

fun String.filterNotNumeric(): String {
    return this.replace("[^\\d.]".toRegex(), "")
}

fun String?.getPhoneFromText(): Phone {
    return Phone.fromNationalNumber(orEmpty().filterNotNumeric().substring(1))
}
