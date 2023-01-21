package app.vazovsky.healsted.data.model

import com.google.gson.annotations.SerializedName

/** Вкладка для экрана с лекарствами */
data class PillsTab(
    /** Имя вкладки */
    @SerializedName("tabName") val tabName: String,

    /** Слот для запроса вкладки */
    @SerializedName("slot") val slot: PillsTabSlot,

    /** Выбрана ли вкладка */
    @SerializedName("selected") val selected: Boolean,
)

/** Слот для вкладки */
enum class PillsTabSlot {
    ALL,
    ACTIVE,
    COMPLETED;
}
