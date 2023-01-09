package app.vazovsky.healsted.extensions

import com.google.firebase.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*

fun LocalDateTime.toMillis(): Long {
    return this.toInstant(ZoneOffset.UTC).toEpochMilli()
}

fun LocalDate.toMillis(): Long {
    return this.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
}

fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this).atZone(ZoneOffset.UTC).toLocalDate()
}

fun Timestamp.toOffsetDateTime(): OffsetDateTime = OffsetDateTime.ofInstant(
    Instant.ofEpochMilli(this.toDate().time),
    ZoneId.systemDefault()
)

fun LocalDate.toStartOfDayTimestamp() = Timestamp(Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant()))