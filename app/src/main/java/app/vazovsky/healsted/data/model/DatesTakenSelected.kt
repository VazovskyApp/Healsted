package app.vazovsky.healsted.data.model

/** Даты для выбора, по каким дням недели отправляется уведомление */
enum class DatesTakenSelected(private val friendlyName: String) {
    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота"),
    SUNDAY("Воскресенье");

    override fun toString() = friendlyName
}
