package app.vazovsky.healsted.data.model

/** Уровень аккаунта */
enum class AccountLevel(
    private val friendlyName: String,
    private val xpCount: Int,
) {
    BACTERIA("Бактерия", 100),
    VIRUS("Вирус", 200),
    INDESTRUCTIBLE("Неубиваемый", 500),
    IMMORTAL("Бессмертный", 1000),
    ETERNAL("Вечный", 2000),
    GOD("БОГ", 5000);

    override fun toString() = friendlyName

    fun getXPCount() = xpCount
}