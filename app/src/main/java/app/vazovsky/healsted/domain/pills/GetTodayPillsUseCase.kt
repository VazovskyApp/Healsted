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

/** Получение лекарств на сегодня в виде QuerySnapshot */
class GetTodayPillsUseCase @Inject constructor(
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<GetTodayPillsUseCase.Params, GetTodayPillsUseCase.Result>() {

    override suspend fun execute(params: Params): Result {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        return Result(
            task = firebaseProfileRepository.fetchProfilePills(uid),
            date = params.date,
        )
    }

    data class Params(
        /** Дата, по которой нужно получить лекарства */
        val date: Timestamp,
    )

    data class Result(
        /** Результат получения лекарств на сегодня */
        val task: Task<QuerySnapshot>,

        /** Дата, по которой нужно получить лекарства */
        val date: Timestamp,
    )
}