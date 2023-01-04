package app.vazovsky.healsted.data.model

/** Группы настроек */
enum class SettingsGroup(private val friendlyName: String) {
    GENERAL("Общее"),
    NOTIFICATION("Уведомления"),
    FEEDBACK("Обратная связь");

    override fun toString() = friendlyName
}
