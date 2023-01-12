package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

/** Получение всех таблеток в виде QuerySnapshot */
class GetAllPillsUseCase @Inject constructor(
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<UseCase.None, Task<QuerySnapshot>>() {

    override suspend fun execute(params: UseCase.None): Task<QuerySnapshot> {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        return firebaseProfileRepository.fetchProfilePills(uid)
    }
}