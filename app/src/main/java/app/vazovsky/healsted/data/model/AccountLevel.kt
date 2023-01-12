package app.vazovsky.healsted.data.model

/** Уровень аккаунта */
enum class AccountLevel(
    private val friendlyName: String,
    /** Количество xp на уровень */
    val xpCount: Int,
) {
    BACTERIA("Бактерия", 100),
    VIRUS("Вирус", 200),
    INDESTRUCTIBLE("Неубиваемый", 500),
    IMMORTAL("Бессмертный", 1000),
    ETERNAL("Вечный", 2000),
    GOD("БОГ", 5000);

    override fun toString() = friendlyName
}