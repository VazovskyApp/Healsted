package app.vazovsky.healsted.data.model

import android.os.Parcelable
import java.time.LocalDate
import java.time.OffsetDateTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pill(
    val id: String,
    val name: String = "",
    val type: PillType,
    val takePillType: TakePillType? = TakePillType.NEVERMIND,
    val dates: List<OffsetDateTime>? = null,
    val datesTaken: DatesTakenType = DatesTakenType.EVERYDAY,
    val datesTakenSelected: List<DatesTakenSelected> = listOf(),
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val amount: Float = 1F,
) : Parcelable