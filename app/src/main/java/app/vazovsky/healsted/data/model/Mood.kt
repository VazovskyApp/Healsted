package app.vazovsky.healsted.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import kotlinx.parcelize.Parcelize

/** Данные о настроении на день */
@Parcelize
data class Mood(
    /** Значение настроения */
    @SerializedName("value") val value: MoodType = MoodType.EMPTY,

    /** Дата отметки настроения */
    @SerializedName("date") val date: LocalDate = LocalDate.now(),
) : Parcelable