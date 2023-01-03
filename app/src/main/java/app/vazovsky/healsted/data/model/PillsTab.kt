package app.vazovsky.healsted.data.model

data class PillsTab(
    /** Имя вкладки */
    val tabName: String,
    /** Слот для запроса вкладки */
    val slot: PillsTabSlot,
    /** Выбрана ли вкладка */
    val selected: Boolean,
)

enum class PillsTabSlot {
    ALL,
    ACTIVE,
    COMPLETED
}
