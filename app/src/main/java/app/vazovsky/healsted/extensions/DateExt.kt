package app.vazovsky.healsted.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

fun LocalDateTime.toMillis(): Long = this.toInstant(ZoneOffset.UTC).toEpochMilli()

fun LocalDate.toMillis(): Long = this.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

fun Long.toLocalDate(): LocalDate = Instant.ofEpochMilli(this).atZone(ZoneOffset.UTC).toLocalDate()

fun LocalDate.toDefaultString(): String = this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault()))
fun LocalTime.toDefaultString(): String = this.format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()))

fun LocalTime.withZeroSecondsAndNano(): LocalTime = this.withSecond(0).withNano(0)

fun LocalTime.toMinutes() = this.hour * 60 + this.minute