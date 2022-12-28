package app.vazovsky.mypills.data.model

enum class MonitoringType(private val friendlyName: String) {
    HEIGHT("Рост"),
    WEIGHT("Вес"),
    TEMPERATURE("Температура"),
    BLOOD_PRESSURE("Давление");

    override fun toString(): String {
        return friendlyName
    }
}