package app.vazovsky.healsted.data.model

enum class SettingsGroup(private val friendlyName: String) {
    GENERAL("Общее"),
    NOTIFICATION("Уведомления"),
    FEEDBACK("Обратная связь");

    override fun toString(): String {
        return friendlyName
    }
}
