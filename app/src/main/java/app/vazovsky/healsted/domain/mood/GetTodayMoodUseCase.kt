package app.vazovsky.healsted.domain.mood

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.extensions.toStartOfDayTimestamp
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import java.time.LocalDate
import javax.inject.Inject

/** Получить настроение за день */
class GetTodayMoodUseCase @Inject constructor(
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<UseCase.None, Task<DocumentSnapshot>>() {

    override suspend fun execute(params: UseCase.None): Task<DocumentSnapshot> {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        return firebaseProfileRepository.fetchProfileTodayMood(uid, LocalDate.now().toStartOfDayTimestamp())
    }
}