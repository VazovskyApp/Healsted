package app.vazovsky.healsted.data.model

import java.time.LocalDate

/** Данные об аккаунте */
data class Account(
    val id: String,
    val nickname: String,
    val phoneNumber: Phone,
    val birthday: LocalDate? = null,
    val avatar: String? = null,
    val level: AccountLevel,
)