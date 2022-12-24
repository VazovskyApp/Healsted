package app.vazovsky.mypills.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MonitoringAttribute(
    val id: String,
    val title: String,
    var value: String,
    val type: MonitoringType,
) : Parcelable