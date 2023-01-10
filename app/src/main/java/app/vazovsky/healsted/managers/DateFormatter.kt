package app.vazovsky.healsted.managers

import android.content.Context
import app.vazovsky.healsted.R
import app.vazovsky.healsted.extensions.capitalizeFirstChar
import app.vazovsky.healsted.extensions.toMillis
import app.vazovsky.healsted.extensions.toOffsetDateTime
import app.vazovsky.healsted.extensions.toStartOfDayTimestamp
import app.vazovsky.healsted.extensions.toTimestamp
import com.google.firebase.Timestamp
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import javax.inject.Inject
import timber.log.Timber

/** Пример: "07.06.21" */
private const val STANDARD_DATE_TEMPLATE = "dd.MM.yy"

/** Пример: "07.06.2021" */
private const val STANDARD_DATE_FULL_TEMPLATE = "dd.MM.yyyy"

/** Пример: "2021.06.07" */
private const val REVERSED_DATE_FULL_TEMPLATE = "yyyy-MM-dd"

/** Пример: "1" */
private const val DAY_TEMPLATE = "d"

/** Пример: "апрель" */
private const val MONTH_TEMPLATE = "LLLL"

/** Пример: "апр." */
private const val MONTH_SHORT_TEMPLATE = "LLL"

/** Пример: "март 2022" */
private const val MONTH_YEAR_TEMPLATE = "LLLL yyyy"

/** Пример: "20:23" */
private const val TIME_TEMPLATE = "HH:mm"

class DateFormatter @Inject constructor(@ApplicationContext val context: Context) {

    val defaultLocale = Locale.getDefault()

    private val standardDateFormat = DateTimeFormatter.ofPattern(STANDARD_DATE_TEMPLATE, Locale.getDefault())
    private val standardDateFullFormat = DateTimeFormatter.ofPattern(STANDARD_DATE_FULL_TEMPLATE, Locale.getDefault())
    private val reversedDateFullFormat = DateTimeFormatter.ofPattern(REVERSED_DATE_FULL_TEMPLATE, Locale.getDefault())
    private val dayFormat = DateTimeFormatter.ofPattern(DAY_TEMPLATE, Locale.getDefault())
    private val timeFormat = DateTimeFormatter.ofPattern(TIME_TEMPLATE, Locale.getDefault())

    // Форматы L в java 8 показывают месяц цифрой
    private val monthFormat = SimpleDateFormat(MONTH_TEMPLATE, Locale.getDefault())
    private val monthShortFormat = SimpleDateFormat(MONTH_SHORT_TEMPLATE, Locale.getDefault())
    private val monthWithYearFormat = SimpleDateFormat(MONTH_YEAR_TEMPLATE, Locale.getDefault())

    /**
     * Форматирует дату в формат
     * @see STANDARD_DATE_TEMPLATE
     *
     * Пример: "07.06.21"
     */
    fun formatStandardDate(date: LocalDate): String {
        return standardDateFormat.format(date)
    }

    /**
     * Форматирует дату в формат
     * @see STANDARD_DATE_TEMPLATE
     *
     * Пример: "07.06.21"
     */
    fun formatStandardDate(date: LocalDateTime): String {
        return standardDateFormat.format(date)
    }

    /**
     * Форматирует дату в формат
     * @see STANDARD_DATE_TEMPLATE
     *
     * Пример: "07.06.21"
     */
    fun formatStandardDate(date: OffsetDateTime): String {
        return standardDateFormat.format(date)
    }

    /**
     * Форматирует дату в формат
     * @see STANDARD_DATE_FULL_TEMPLATE
     *
     * Пример: "07.06.2021"
     */
    fun formatStandardDateFull(date: OffsetDateTime): String {
        return standardDateFullFormat.format(date)
    }

    /**
     * Форматирует дату в формат
     * @see STANDARD_DATE_FULL_TEMPLATE
     *
     * Пример: "07.06.2021"
     */
    fun formatStandardDateFull(date: LocalDate): String {
        return standardDateFullFormat.format(date)
    }

    /**
     * Форматирует дату в формат
     * @see STANDARD_DATE_FULL_TEMPLATE
     *
     * Пример: "17.03.1999"
     */
    fun formatStandardDateFull(date: LocalDateTime): String {
        return standardDateFullFormat.format(date)
    }

    /**
     * Форматирует дату в формат
     * @see DAY_TEMPLATE
     *
     * Пример: "17"
     */
    fun formatDay(date: LocalDate): String {
        return dayFormat.format(date)
    }

    /**
     * Форматирует дату в формат
     * @see DAY_TEMPLATE
     *
     * Пример: "17"
     */
    fun formatDay(date: LocalDateTime): String {
        return dayFormat.format(date)
    }

    /**
     * Форматирует дату в формат
     * @see TIME_TEMPLATE
     *
     * Пример: "20:23"
     */
    fun formatTime(date: LocalDateTime): String {
        return timeFormat.format(date)
    }

    /**
     * Форматирует дату в формат
     * @see TIME_TEMPLATE
     *
     * Пример: "20:23"
     */
    fun formatTime(date: OffsetTime): String {
        return timeFormat.format(date)
    }

    fun formatTime(time: Timestamp): String {
        return formatTime(time.toOffsetDateTime().toOffsetTime())
    }

    fun parseDateFromString(dateString: String): LocalDate {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    }

    fun parseTimeFromString(timeString: String): Timestamp {
        Timber.d("parse: ${LocalDateTime.parse(timeString, DateTimeFormatter.ofPattern(TIME_TEMPLATE, defaultLocale))}")
        return LocalDateTime.parse(timeString, DateTimeFormatter.ofPattern(TIME_TEMPLATE)).toTimestamp()
    }

    /**
     * Форматирует дату в формат
     * @see MONTH_TEMPLATE
     *
     * Пример: "Март"
     */
    fun formatMonth(date: LocalDate): String {
        return monthFormat.format(date.toMillis()).capitalizeFirstChar(defaultLocale)
    }

    /**
     * Форматирует дату в формат без точки, месяц из трех символов
     * @see MONTH_SHORT_TEMPLATE
     *
     * Пример: "Мар"
     */
    fun formatShortMonth(date: LocalDate): String {
        return monthShortFormat
            .format(date.toMillis())
            .capitalizeFirstChar(defaultLocale)
            .replace(".", "")
            .substring(0, 3)
    }

    /**
     * Форматирует дату в формат
     * @see MONTH_YEAR_TEMPLATE
     *
     * Пример: "март 1999"
     */
    fun formatMonthWithYear(date: OffsetDateTime): String {
        return monthWithYearFormat.format(date.toLocalDate().toMillis())
    }

    /** Форматирует дату в вид без года и с днем недели
     * Пример: 17 марта, Среда */
    fun formatDateWithDayOfWeek(day: LocalDate): String = buildString {
        append(day.dayOfMonth)
        append(" ")
        append(day.month.getDisplayName(TextStyle.FULL, defaultLocale))
        append(", ")
        append(day.dayOfWeek.getDisplayName(TextStyle.FULL, defaultLocale).capitalizeFirstChar(defaultLocale))
    }

    /**
     * Возвращает строку с положением второй даты относительно первой
     * @param firstDate сегодняшняя дата
     * @param secondDate сравниваемая дата
     */
    fun getDisplayDifferentDates(firstDate: LocalDate, secondDate: LocalDate) =
        context.getString(
            when (secondDate) {
                firstDate -> R.string.dashboard_date_today
                in LocalDate.MIN..firstDate -> R.string.dashboard_date_early
                in firstDate..LocalDate.MAX -> R.string.dashboard_date_later
                // Маловероятный сценарий
                else -> R.string.dashboard_date_unknown
            }
        )

    /**
     * Форматирует даты в период
     * Пример: "17 марта 1999 - 6 января 2023"
     */
    fun formatPeriod(firstDay: Timestamp, lastDay: Timestamp?): String {
        val firstDayOffset = firstDay.toOffsetDateTime()
        val lastDayOffset = lastDay?.toOffsetDateTime()
        return when {
            lastDayOffset == null -> formatEndlessPeriod(firstDayOffset)
            firstDayOffset.month == lastDayOffset.month -> formatDatesOneMonth(firstDayOffset, lastDayOffset)
            firstDayOffset.year == lastDayOffset.year -> formatDatesOneYears(firstDayOffset, lastDayOffset)
            else -> formatDatesOther(firstDayOffset, lastDayOffset)
        }
    }

    private fun formatEndlessPeriod(firstDay: OffsetDateTime): String = buildString {
        append("${firstDay.dayOfMonth} ${firstDay.month.getDisplayName(TextStyle.FULL, defaultLocale)}")
        append(" - ")
        append(context.getString(R.string.mnemocode_endless))
    }

    /**
     * Форматирует дату в период, если у крайних его дат один год
     */
    private fun formatDatesOneYears(firstDay: OffsetDateTime, lastDay: OffsetDateTime): String = buildString {
        append(firstDay.dayOfMonth)
        append(" ")
        append(firstDay.month.getDisplayName(TextStyle.FULL, defaultLocale))
        append(" - ")
        append(lastDay.dayOfMonth)
        append(" ")
        append(lastDay.month.getDisplayName(TextStyle.FULL, defaultLocale))
        append(" ")
        append(lastDay.year)
    }

    /**
     * Форматирует дату в период, если у крайних дат его один месяц
     */
    private fun formatDatesOneMonth(firstDay: OffsetDateTime, lastDay: OffsetDateTime): String = buildString {
        append(firstDay.dayOfMonth)
        append(" - ")
        append(lastDay.dayOfMonth)
        append(" ")
        append(firstDay.month.getDisplayName(TextStyle.FULL, defaultLocale))
        append(" ")
        append(firstDay.year)
    }

    /**
     * Форматирует дату в период, если у крайних дат его разные месяца и года
     */
    private fun formatDatesOther(firstDay: OffsetDateTime, lastDay: OffsetDateTime): String = buildString {
        append(firstDay.dayOfMonth)
        append(" ")
        append(firstDay.month.getDisplayName(TextStyle.FULL, defaultLocale))
        append(" ")
        append(firstDay.year)
        append(" - ")
        append(lastDay.dayOfMonth)
        append(" ")
        append(lastDay.month.getDisplayName(TextStyle.FULL, defaultLocale))
        append(" ")
        append(lastDay.year)
    }

    /**
     * Форматирует дату в вид
     * Пример: "17 марта"
     */
    fun formatDayMonth(date: OffsetDateTime): String = buildString {
        append(date.dayOfMonth)
        append(" ")
        append(date.month.getDisplayName(TextStyle.FULL, defaultLocale))
    }

    /**
     * Форматирует дату в формат
     * @see REVERSED_DATE_FULL_TEMPLATE
     */
    fun formatReversedDateFull(date: LocalDate): String {
        return reversedDateFullFormat.format(date)
    }
}
