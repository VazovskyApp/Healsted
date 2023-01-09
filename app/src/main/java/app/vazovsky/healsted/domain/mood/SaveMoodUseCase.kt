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
import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

/** Добавление настроений в аккаунт */
class SaveMoodUseCase @Inject constructor(
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