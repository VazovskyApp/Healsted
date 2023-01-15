package app.vazovsky.healsted.data.model

/** Тип настроек */
enum class SettingType(private val friendlyName: String) {
    ACCOUNT("Аккаунт"),
    NOTIFICATION("Уведомления"),
    ABOUT_US("О приложении"),
    LOG_OUT("Выйти");

    override fun toString() = friendlyName
}
