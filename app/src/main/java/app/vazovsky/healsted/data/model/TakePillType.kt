package app.vazovsky.healsted.data.model

/** Тип принятия лекарства в зависимости от еды */
enum class TakePillType(private val friendlyName: String) {
    BEFORE_MEALS("До еды"),
    AFTER_MEALS("После еды"),
    WITH_FOOD("Во время еды"),
    NEVERMIND("Неважно");

    override fun toString() = friendlyName
}

fun String.convertTakePillTypeFromString() = TakePillType.valueOf(this)