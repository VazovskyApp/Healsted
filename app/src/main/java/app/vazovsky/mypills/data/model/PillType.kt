package app.vazovsky.mypills.data.model

enum class PillType(private val friendlyName: String) {
    TABLETS("Таблетки"),
    CAPSULE("Капсулы"),
    INJECTION("Инъекции"),
    PROCEDURES("Процедуры"),
    DROPS("Капли"),
    LIQUID("Жидкость"),
    CREAM("Крем"),
    SPRAY("Спрей");

    override fun toString(): String {
        return friendlyName
    }
}