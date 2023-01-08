package app.vazovsky.healsted.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

/** Атрибут мониторинга здоровья */
@Parcelize
data class MonitoringAttribute(
    /** Значение */
    var value: String,

    /** Тип атрибута */
    val type: MonitoringType,

    /** Дата отметки значения */
    val date: Timestamp,
) : Parcelable