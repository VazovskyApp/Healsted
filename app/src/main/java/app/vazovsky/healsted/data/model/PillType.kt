package app.vazovsky.healsted.data.model

/** Тип лекарства */
enum class PillType(private val friendlyName: String) {
    TABLETS("Таблетки"),
    CAPSULE("Капсулы"),
    INJECTION("Инъекции"),
    PROCEDURES("Процедуры"),
    DROPS("Капли"),
    LIQUID("Жидкость"),
    CREAM("Крем"),
    SPRAY("Спрей");

    override fun toString() = friendlyName

}

fun String.convertPillTypeFromString() = PillType.valueOf(this)
