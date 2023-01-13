package app.vazovsky.healsted.data.firebase.model

import android.os.Parcelable
import app.vazovsky.healsted.data.model.User
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

/** Данные об аккаунте */
@Parcelize
data class AccountDocument(
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
    @PropertyName("birthday") val birthday: String? = null,

    /** Аватар */
    @PropertyName("avatar") val avatar: String? = null,
) : Parcelable