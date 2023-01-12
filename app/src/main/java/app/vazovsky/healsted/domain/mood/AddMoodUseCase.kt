package app.vazovsky.healsted.domain.mood

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.data.model.MoodType
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.extensions.toStartOfDayTimestamp
import com.google.android.gms.tasks.Task
import java.time.LocalDate
import javax.inject.Inject

/** Добавление настроения с текущим значением */
class AddMoodUseCase @Inject constructor(
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<UseCase.None, Task<Void>>() {

    override suspend fun execute(params: UseCase.None): Task<Void> {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        return firebaseProfileRepository.addProfileMood(
            uid = uid,
            mood = Mood(
                MoodType.EMPTY,
                LocalDate.now().toStartOfDayTimestamp(),
            ),
        )
    }
}