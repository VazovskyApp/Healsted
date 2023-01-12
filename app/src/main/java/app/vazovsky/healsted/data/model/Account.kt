package app.vazovsky.healsted.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

/** Данные об аккаунте */
data class Account(
    /** Владелец аккаунта */
    @PropertyName("accountHolder") val accountHolder: User = User(),

    /** Никнейм */
    @PropertyName("nickname") val nickname: String = "",

    /** Имя */
    @PropertyName("name") val name: String = "",

    /** Фамилия */
    @PropertyName("surname") val surname: String = "",

    /** Отчество */
    @PropertyName("patronymic") val patronymic: String = "",

    /** Дата рождения */
    @PropertyName("birthday") val birthday: Timestamp? = null,

    /** Аватар */
    @PropertyName("avatar") val avatar: String? = null,
)