package app.vazovsky.healsted.data.model

enum class DatesTakenType(private val friendlyName: String) {
    EVERYDAY("Каждый день"),
    IN_A_DAY("Через день"),
    SELECTED_DAYS("По выбранным дням");

    override fun toString(): String {
        return friendlyName
    }
}