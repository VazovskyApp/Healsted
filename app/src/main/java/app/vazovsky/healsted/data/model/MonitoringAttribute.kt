package app.vazovsky.healsted.data.model

import android.os.Parcelable
import java.time.LocalDate
import kotlinx.parcelize.Parcelize

@Parcelize
data class MonitoringAttribute(
    var value: String,
    val type: MonitoringType,
    val date: LocalDate,
) : Parcelable