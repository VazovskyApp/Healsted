package app.vazovsky.healsted.data.firebase.model

import android.os.Parcelable
import app.vazovsky.healsted.data.model.MoodType
import app.vazovsky.healsted.extensions.toTodayString
import com.google.firebase.firestore.PropertyName
import java.time.LocalDate
import java.util.*
import kotlinx.parcelize.Parcelize

/** Данные о настроении на день */
@Parcelize
data class MoodDocument(
    /** Значение настроения */
    @PropertyName("value") val value: MoodType = MoodType.EMPTY,

    /** Дата отметки настроения */
    @PropertyName("date") val date: String = LocalDate.now().toTodayString(),
) : Parcelable