package app.vazovsky.healsted.data.model

/** Тип атрибута мониторинга здоровья */
enum class MonitoringType(private val friendlyName: String) {
    HEIGHT("Рост"),
    WEIGHT("Вес"),
    TEMPERATURE("Температура"),
    BLOOD_PRESSURE("Давление");

    override fun toString() = friendlyName
}