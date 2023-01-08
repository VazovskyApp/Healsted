package app.vazovsky.healsted.data.model

import com.google.firebase.Timestamp


/** Данные о настроении на день */
data class Mood(
    /** Значение настроения */
    val value: MoodType = MoodType.EMPTY,

    /** Дата отметки настроения */
    val date: Timestamp,
)