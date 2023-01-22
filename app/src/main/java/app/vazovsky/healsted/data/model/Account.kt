package app.vazovsky.healsted.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

/** Данные об аккаунте */
data class Account(
    /** Владелец аккаунта */
    @SerializedName("accountHolder") val accountHolder: User = User(),

    /** Никнейм */
    @SerializedName("nickname") val nickname: String = "",

    /** Имя */
    @SerializedName("name") val name: String = "",

    /** Фамилия */
    @SerializedName("surname") val surname: String = "",

    /** Отчество */
    @SerializedName("patronymic") val patronymic: String = "",

    /** Дата рождения */
    @SerializedName("birthday") val birthday: LocalDate? = null,

    /** Аватар */
    @SerializedName("avatar") val avatar: String? = null,
)