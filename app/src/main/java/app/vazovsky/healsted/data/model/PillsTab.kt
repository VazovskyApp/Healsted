package app.vazovsky.healsted.data.model

/** Вкладка для экрана с лекарствами */
data class PillsTab(
    /** Имя вкладки */
    val tabName: String,
    /** Слот для запроса вкладки */
    val slot: PillsTabSlot,
    /** Выбрана ли вкладка */
    val selected: Boolean,
)

/** Слот для вкладки */
enum class PillsTabSlot {
    ALL,
    ACTIVE,
    COMPLETED;
}
