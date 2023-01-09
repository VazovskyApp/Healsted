package app.vazovsky.healsted.domain.profile

import app.vazovsky.healsted.data.model.LoyaltyProgress
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orError
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class ParseSnapshotToLoyaltyUseCase @Inject constructor() :
    UseCaseUnary<ParseSnapshotToLoyaltyUseCase.Params, LoyaltyProgress>() {

    override suspend fun execute(params: Params): LoyaltyProgress {
        return params.snapshot.toObject(LoyaltyProgress::class.java).orError()
    }

    data class Params(
        val snapshot: DocumentSnapshot,
    )
}