package app.vazovsky.healsted.domain.account

import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orError
import app.vazovsky.healsted.extensions.toDataClass
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

/** Парсинг DocumentSnapshot в аккаунт */
class ParseSnapshotToAccountUseCase @Inject constructor() : UseCaseUnary<ParseSnapshotToAccountUseCase.Params, Account>() {

    override suspend fun execute(params: Params): Account {
        return params.snapshot.data?.toDataClass<Account>().orError()
    }

    data class Params(
        /** Форматируемый DocumentSnapshot с аккаунтом */
        val snapshot: DocumentSnapshot,
    )
}