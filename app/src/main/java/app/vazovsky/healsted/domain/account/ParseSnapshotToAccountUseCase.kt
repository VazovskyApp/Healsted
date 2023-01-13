package app.vazovsky.healsted.domain.account

import app.vazovsky.healsted.data.firebase.model.AccountDocument
import app.vazovsky.healsted.data.mapper.AccountMapper
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orError
import app.vazovsky.healsted.extensions.toDataClass
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

/** Парсинг DocumentSnapshot в аккаунт */
class ParseSnapshotToAccountUseCase @Inject constructor(
    private val accountMapper: AccountMapper,
) : UseCaseUnary<ParseSnapshotToAccountUseCase.Params, Account>() {

    override suspend fun execute(params: Params): Account {
        val accountDocument = params.snapshot.data?.toDataClass<AccountDocument>().orError()
        return accountMapper.fromDocumentToModel(accountDocument)
    }

    data class Params(
        /** Форматируемый DocumentSnapshot с аккаунтом */
        val snapshot: DocumentSnapshot,
    )
}