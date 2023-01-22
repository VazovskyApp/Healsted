package app.vazovsky.healsted.data.firebase.model

import android.os.Parcelable
import app.vazovsky.healsted.data.model.MoodType
import app.vazovsky.healsted.extensions.toDefaultString
import com.google.firebase.firestore.PropertyName
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*
import kotlinx.parcelize.Parcelize

/** Данные о настроении на день */
@Parcelize
data class MoodDocument(
    /** Значение настроения */
    @SerializedName("value") @PropertyName("value") val value: MoodType = MoodType.EMPTY,

    /** Дата отметки настроения */
    @SerializedName("date") @PropertyName("date") val date: String = LocalDate.now().toDefaultString(),
) : Parcelable