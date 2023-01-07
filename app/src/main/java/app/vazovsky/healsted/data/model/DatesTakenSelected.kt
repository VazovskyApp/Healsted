package app.vazovsky.healsted.data.model

/** Даты для выбора, по каким дням недели отправляется уведомление */
enum class DatesTakenSelected {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY,
}

fun String.convertDatesTakenSelectedFromString() = DatesTakenSelected.valueOf(this)