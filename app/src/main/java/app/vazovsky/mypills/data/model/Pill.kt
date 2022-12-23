package app.vazovsky.mypills.data.model

import java.time.OffsetDateTime

data class Pill(
    val id: String,
    val name: String = "",
    val type: PillType,
    val takePillType: TakePillType? = TakePillType.NEVERMIND,
    val dates: List<OffsetDateTime>? = null,
    val amount: Float = 1F,
)