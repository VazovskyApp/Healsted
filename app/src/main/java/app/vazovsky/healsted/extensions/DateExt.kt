package app.vazovsky.healsted.extensions

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

fun LocalDate.toDefaultString(): String = this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault()))
fun LocalTime.toDefaultString(): String = this.format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()))