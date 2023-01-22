package app.vazovsky.healsted.data.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import kotlinx.parcelize.Parcelize

/** Атрибут мониторинга здоровья */
@Parcelize
data class MonitoringAttribute(
    /** Значение */
    @SerializedName("value") var value: String = "0",

    /** Тип атрибута */
    @SerializedName("type") val type: MonitoringType = MonitoringType.BLOOD_PRESSURE,

    /** Дата отметки значения */
    @SerializedName("date") val date: LocalDate = LocalDate.now(),
) : Parcelable