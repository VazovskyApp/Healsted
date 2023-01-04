package app.vazovsky.healsted.data.model

import java.time.OffsetDateTime

/** Данные о настроении на день */
data class Mood(
    /** ID настроения */
    val id: String,

    /** Значение настроения */
    val value: MoodType = MoodType.EMPTY,

    /** Дата отметки настроения */
    val date: OffsetDateTime,
)