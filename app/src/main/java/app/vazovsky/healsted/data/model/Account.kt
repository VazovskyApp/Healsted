package app.vazovsky.healsted.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

/** Данные об аккаунте */
data class Account(
    @PropertyName("accountHolder") val accountHolder: User = User(),
    @PropertyName("nickname") val nickname: String = "",
    @PropertyName("name") val name: String = "",
    @PropertyName("surname") val surname: String = "",
    @PropertyName("patronymic") val patronymic: String = "",
    @PropertyName("birthday") val birthday: Timestamp? = null,
    @PropertyName("avatar") val avatar: String? = null,
)