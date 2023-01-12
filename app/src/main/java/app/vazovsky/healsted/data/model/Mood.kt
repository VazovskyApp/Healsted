package app.vazovsky.healsted.data.model

import android.os.Parcelable
import app.vazovsky.healsted.extensions.toStartOfDayTimestamp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import java.time.LocalDate
import kotlinx.parcelize.Parcelize

/** Данные о настроении на день */
@Parcelize
data class Mood(
    /** Значение настроения */
    @PropertyName("value") val value: MoodType = MoodType.EMPTY,

    /** Дата отметки настроения */
    @PropertyName("date") val date: Timestamp = LocalDate.now().toStartOfDayTimestamp(),
) : Parcelable