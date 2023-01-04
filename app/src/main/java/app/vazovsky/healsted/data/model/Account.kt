package app.vazovsky.healsted.data.model

import java.time.LocalDate

/** Данные об аккаунте */
data class Account(
    val accountHolder: User,
    val nickname: String,
    val name: String = "",
    val surname: String = "",
    val patronymic: String = "",
    val birthday: LocalDate? = null,
    val avatar: String? = null,
    val level: AccountLevel = AccountLevel.BACTERIA,
)