package app.vazovsky.healsted.data.model

/** Пользователь */
data class User(
    /** Почта */
    val email: String,

    /** Номер телефона */
    var phoneNumber: String?,
)