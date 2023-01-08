package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.model.PillsTabSlot
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

/** Получение данных о лекарствах */
class GetPillsUseCase @Inject constructor(
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<GetPillsUseCase.Params, GetPillsUseCase.Result>() {

    override suspend fun execute(params: Params): Result {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        return Result(
            snapshot = firebaseProfileRepository.fetchProfilePills(uid),
            slot = params.slot,
        )
    }

    data class Params(
        val slot: PillsTabSlot?,
    )

    data class Result(
        val snapshot: Task<QuerySnapshot>,
        val slot: PillsTabSlot?,
    )
}