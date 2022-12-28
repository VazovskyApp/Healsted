package app.vazovsky.mypills.data.model

enum class TakePillType(private val friendlyName: String) {
    BEFORE_MEALS("До еды"),
    AFTER_MEALS("После еды"),
    WITH_FOOD("Во время еды"),
    NEVERMIND("Неважно");

    override fun toString(): String {
        return friendlyName
    }
}