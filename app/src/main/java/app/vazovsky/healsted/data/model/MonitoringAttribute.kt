package app.vazovsky.healsted.data.model

import android.os.Parcelable
import app.vazovsky.healsted.extensions.toStartOfDayTimestamp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import java.time.LocalDate
import kotlinx.parcelize.Parcelize

/** Атрибут мониторинга здоровья */
@Parcelize
data class MonitoringAttribute(
    /** Значение */
    @PropertyName("value") var value: String = "0",

    /** Тип атрибута */
    @PropertyName("type") val type: MonitoringType = MonitoringType.BLOOD_PRESSURE,

    /** Дата отметки значения */
    @PropertyName("date") val date: Timestamp = LocalDate.now().toStartOfDayTimestamp(),
) : Parcelable