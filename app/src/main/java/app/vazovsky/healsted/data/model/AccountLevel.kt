package app.vazovsky.healsted.data.model

enum class AccountLevel(private val friendlyName: String) {
    BACTERIA("Бактерия"),
    VIRUS("Вирус"),
    INDESTRUCTIBLE("Неубиваемый"),
    IMMORTAL("Бессмертный"),
    ETERNAL("Вечный"),
    GOD("БОГ");

    override fun toString(): String {
        return friendlyName
    }
}