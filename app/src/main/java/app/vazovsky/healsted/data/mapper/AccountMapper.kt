package app.vazovsky.healsted.data.mapper

import app.vazovsky.healsted.data.firebase.model.AccountDocument
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.managers.DateFormatter
import javax.inject.Inject

class AccountMapper @Inject constructor(
    private val dateFormatter: DateFormatter,
) {
    fun fromDocumentToModel(document: AccountDocument): Account {
        return Account(
            accountHolder = document.accountHolder,
            nickname = document.nickname,
            name = document.name,
            surname = document.surname,
            patronymic = document.patronymic,
            birthday = document.birthday?.let { dateFormatter.parseLocalDateFromString(it) },
            avatar = document.avatar,
        )
    }

    fun fromModelToDocument(model: Account): AccountDocument {
        return AccountDocument(
            accountHolder = model.accountHolder,
            nickname = model.nickname,
            name = model.name,
            surname = model.surname,
            patronymic = model.patronymic,
            birthday = model.birthday?.let { dateFormatter.formatStringFromLocalDate(it) },
            avatar = model.avatar,
        )
    }
}