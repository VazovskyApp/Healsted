package app.vazovsky.mypills.data.model

enum class AccountLevel(private val friendlyName: String) {
    BEGINNER("Начинающий"),
    BIG_GUY("Здоровяк"),
    PRETTY_GUY("Красавчик");

    override fun toString(): String {
        return friendlyName
    }
}