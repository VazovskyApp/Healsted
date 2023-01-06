package app.vazovsky.healsted.data.model

import com.google.firebase.firestore.PropertyName

/** Пользователь */
data class User(
    /** Почта */
    @PropertyName("email") val email: String = "",

    /** Номер телефона */
    @PropertyName("phoneNumber") var phoneNumber: String = "",
)