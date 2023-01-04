package app.vazovsky.healsted.data.model

import android.os.Parcelable
import java.time.LocalDate
import kotlinx.parcelize.Parcelize

/** Атрибут мониторинга здоровья */
@Parcelize
data class MonitoringAttribute(
    /** Значение */
    var value: String,

    /** Тип атрибута */
    val type: MonitoringType,

    /** Дата отметки значения */
    val date: LocalDate,
) : Parcelable