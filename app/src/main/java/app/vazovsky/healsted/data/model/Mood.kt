package app.vazovsky.healsted.data.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import java.time.LocalDate
import kotlinx.parcelize.Parcelize

/** Данные о настроении на день */
@Parcelize
data class Mood(
    /** Значение настроения */
    @PropertyName("value") val value: MoodType = MoodType.EMPTY,

    /** Дата отметки настроения */
    @PropertyName("date") val date: LocalDate = LocalDate.now(),
) : Parcelable