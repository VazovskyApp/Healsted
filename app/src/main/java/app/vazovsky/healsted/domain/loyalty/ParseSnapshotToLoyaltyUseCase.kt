package app.vazovsky.healsted.domain.loyalty

import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orError
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

/** Парсинг из DocumentSnapshot в уровень аккаунта */
class ParseSnapshotToLoyaltyUseCase @Inject constructor() :
    UseCaseUnary<ParseSnapshotToLoyaltyUseCase.Params, LoyaltyProgress>() {

    override suspend fun execute(params: Params): LoyaltyProgress {
        return params.snapshot.toObject(LoyaltyProgress::class.java).orError()
    }

    data class Params(
        /** Форматируемый DocumentSnapshot с уровнем аккаунта */
        val snapshot: DocumentSnapshot,
    )
}