package app.vazovsky.healsted.domain.profile

import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orError
import app.vazovsky.healsted.extensions.toDataClass
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class ParseSnapshotToProfileUseCase @Inject constructor() : UseCaseUnary<ParseSnapshotToProfileUseCase.Params, Account>() {

    override suspend fun execute(params: Params): Account {
        return params.snapshot.data?.toDataClass<Account>().orError()
    }

    data class Params(
        val snapshot: DocumentSnapshot,
    )
}