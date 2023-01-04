package app.vazovsky.healsted.data.model

/** Тип значения настроения */
enum class MoodType(private val friendlyName: String) {
    EMPTY("Нет"),
    AWFUL("Ужасно"),
    BAD("Плохо"),
    OKAY("Нормально"),
    GOOD("Хорошо"),
    GREAT("Отлично");

    override fun toString() = friendlyName
}