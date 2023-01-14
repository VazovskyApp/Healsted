package app.vazovsky.healsted.data.model

import java.time.LocalDate
import java.time.LocalTime

/** Элемент лекарств на сегодня */
data class TodayPillItem(
    /** Данные о лекарстве */
    val pill: Pill,

    /** Дата приема */
    val date: LocalDate,

    /** Время приема */
    val time: LocalTime,
)