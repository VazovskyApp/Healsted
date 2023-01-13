package app.vazovsky.healsted.managers

import android.content.Context
import app.vazovsky.healsted.R
import app.vazovsky.healsted.extensions.capitalizeFirstChar
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import javax.inject.Inject

/** Пример: "20:23" */
private const val TIME_TEMPLATE = "HH:mm"

private const val DATE_TEMPLATE = "dd.MM.yyyy"

class DateFormatter @Inject constructor(@ApplicationContext val context: Context) {

    private val defaultLocale: Locale = Locale.getDefault()

    private val timeFormat = DateTimeFormatter.ofPattern(TIME_TEMPLATE, defaultLocale)
    private val dateFormat = DateTimeFormatter.ofPattern(DATE_TEMPLATE, defaultLocale)

    private val timeSimpleFormat = SimpleDateFormat(TIME_TEMPLATE, defaultLocale)
    private val dateSimpleFormat = SimpleDateFormat(DATE_TEMPLATE, defaultLocale)

    /** Парсинг локального времени из строки */
    fun parseLocalTimeFromString(timeString: String): LocalTime {
        return LocalTime.parse(timeString, timeFormat)
    }

    /** Парсинг локальной даты из строки */
    fun parseLocalDateFromString(dateString: String): LocalDate {
        return LocalDate.parse(dateString, dateFormat)
    }

    /** Форматирование строки из локальной даты */
    fun formatStringFromLocalDate(date: LocalDate): String {
        return date.format(dateFormat)
    }

    /** Форматирование строки из локального времени */
    fun formatStringFromLocalTime(time: LocalTime): String {
        return time.format(timeFormat)
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
    fun formatPeriod(firstDay: LocalDate, lastDay: LocalDate?): String {
        return when {
            lastDay == null -> formatEndlessPeriod(firstDay)
            firstDay.month == lastDay.month -> formatDatesOneMonth(firstDay, lastDay)
            firstDay.year == lastDay.year -> formatDatesOneYears(firstDay, lastDay)
            else -> formatDatesOther(firstDay, lastDay)
        }
    }

    private fun formatEndlessPeriod(firstDay: LocalDate): String = buildString {
        append("${firstDay.dayOfMonth} ${firstDay.month.getDisplayName(TextStyle.FULL, defaultLocale)}")
        append(" - ")
        append(context.getString(R.string.mnemocode_endless))
    }

    /**
     * Форматирует дату в период, если у крайних его дат один год
     */
    private fun formatDatesOneYears(firstDay: LocalDate, lastDay: LocalDate): String = buildString {
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
    private fun formatDatesOneMonth(firstDay: LocalDate, lastDay: LocalDate): String = buildString {
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
    private fun formatDatesOther(firstDay: LocalDate, lastDay: LocalDate): String = buildString {
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
}
