package app.vazovsky.healsted.data.firebase.model

import android.os.Parcelable
import app.vazovsky.healsted.data.model.MonitoringType
import app.vazovsky.healsted.extensions.toDefaultString
import com.google.firebase.firestore.PropertyName
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*
import kotlinx.parcelize.Parcelize

/** Атрибут мониторинга здоровья */
@Parcelize
data class MonitoringAttributeDocument(
    /** Значение */
    @SerializedName("value") @PropertyName("value") var value: String = "0",

    /** Тип атрибута */
    @SerializedName("type") @PropertyName("type") val type: MonitoringType = MonitoringType.BLOOD_PRESSURE,

    /** Дата отметки значения */
    @SerializedName("date") @PropertyName("date") val date: String = LocalDate.now().toDefaultString(),
) : Parcelable