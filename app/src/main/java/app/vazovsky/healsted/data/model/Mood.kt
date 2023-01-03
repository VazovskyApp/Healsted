package app.vazovsky.healsted.data.model

import java.time.OffsetDateTime

data class Mood(
    val id: String,
    val value: MoodType = MoodType.EMPTY,
    val date: OffsetDateTime,
)