package app.vazovsky.healsted.data.model

import com.google.gson.annotations.SerializedName

data class DatesTakenSelectedItem(
    /** День */
    @SerializedName("value") val value: Int,

    /** Выбран ли день */
    @SerializedName("selected") var selected: Boolean = false,
)