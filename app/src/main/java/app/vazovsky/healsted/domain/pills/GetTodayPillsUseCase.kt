package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class GetTodayPillsUseCase @Inject constructor(
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<GetTodayPillsUseCase.Params, GetTodayPillsUseCase.Result>() {

    override suspend fun execute(params: Params): Result {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        return Result(
            snapshotTask = firebaseProfileRepository.fetchProfilePills(uid),
            date = params.date,
        )
    }

    data class Params(
        val date: Timestamp,
    )

    data class Result(
        val snapshotTask: Task<QuerySnapshot>,
        val date: Timestamp,
    )
}