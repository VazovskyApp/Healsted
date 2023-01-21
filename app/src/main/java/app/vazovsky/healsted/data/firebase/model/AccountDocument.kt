package app.vazovsky.healsted.data.firebase.model

import android.os.Parcelable
import app.vazovsky.healsted.data.model.User
import com.google.firebase.firestore.PropertyName
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/** Данные об аккаунте */
@Parcelize
data class AccountDocument(
    /** Владелец аккаунта */
    @SerializedName("accountHolder") @PropertyName("accountHolder") val accountHolder: User = User(),

    /** Никнейм */
    @SerializedName("nickname") @PropertyName("nickname") val nickname: String = "",

    /** Имя */
    @SerializedName("name") @PropertyName("name") val name: String = "",

    /** Фамилия */
    @SerializedName("surname") @PropertyName("surname") val surname: String = "",

    /** Отчество */
    @SerializedName("patronymic") @PropertyName("patronymic") val patronymic: String = "",

    /** Дата рождения */
    @SerializedName("birthday") @PropertyName("birthday") val birthday: String? = null,

    /** Аватар */
    @SerializedName("avatar") @PropertyName("avatar") val avatar: String? = null,
) : Parcelable