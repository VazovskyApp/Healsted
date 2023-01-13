package app.vazovsky.healsted.data.firebase.model

import android.os.Parcelable
import app.vazovsky.healsted.data.model.MonitoringType
import app.vazovsky.healsted.extensions.toTodayString
import com.google.firebase.firestore.PropertyName
import java.time.LocalDate
import java.util.*
import kotlinx.parcelize.Parcelize

/** Атрибут мониторинга здоровья */
@Parcelize
data class MonitoringAttributeDocument(
    /** Значение */
    @PropertyName("value") var value: String = "0",

    /** Тип атрибута */
    @PropertyName("type") val type: MonitoringType = MonitoringType.BLOOD_PRESSURE,

    /** Дата отметки значения */
    @PropertyName("date") val date: String = LocalDate.now().toTodayString(),
) : Parcelable