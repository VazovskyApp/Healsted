package app.vazovsky.healsted.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun LocalDate.toTodayString(): String = this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault()))