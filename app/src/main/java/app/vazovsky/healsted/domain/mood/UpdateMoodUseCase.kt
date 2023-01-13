package app.vazovsky.healsted.domain.mood

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.mapper.MoodMapper
import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import com.google.android.gms.tasks.Task
import javax.inject.Inject

/** Обновить сегодняшнее настроение */
class UpdateMoodUseCase @Inject constructor(
    private val moodMapper: MoodMapper,
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<UpdateMoodUseCase.Params, Task<Void>>() {

    override suspend fun execute(params: Params): Task<Void> {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        return firebaseProfileRepository.updateProfileMood(uid, moodMapper.fromModelToDocument(params.mood))
    }

    data class Params(
        /** Текущее настроение */
        val mood: Mood,
    )
}