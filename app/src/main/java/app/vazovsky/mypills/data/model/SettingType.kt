package app.vazovsky.mypills.data.model

enum class SettingType(private val friendlyName: String) {
    ACCOUNT("Аккаунт"),
    NOTIFICATION("Уведомления"),
    REPORT_A_BAG("Рассказать о баге"),
    SEND_FEEDBACK("Отправить обратную связь"),
    ABOUT_US("О приложении"),
    LOG_OUT("Выйти");

    override fun toString(): String {
        return friendlyName
    }
}
