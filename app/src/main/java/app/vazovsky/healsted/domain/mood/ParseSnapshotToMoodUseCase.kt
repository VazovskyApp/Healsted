package app.vazovsky.healsted.domain.mood

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.data.model.MoodType
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.extensions.orError
import app.vazovsky.healsted.extensions.toDataClass
import app.vazovsky.healsted.extensions.toStartOfDayTimestamp
import com.google.firebase.firestore.DocumentSnapshot
import java.time.LocalDate
import javax.inject.Inject
import timber.log.Timber

/** TODO Есть задержка, нужен лоадер */
/** Парсинг DocumentSnapshot в настроение */
class ParseSnapshotToMoodUseCase @Inject constructor(
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<ParseSnapshotToMoodUseCase.Params, Mood>() {

    override suspend fun execute(params: Params): Mood {
        val result = params.snapshot.data?.toDataClass<Mood>()
        return if (result == null) {
            val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()
            val newMood = Mood()
            firebaseProfileRepository.addProfileMood(uid, newMood)
            newMood
        } else result
    }

    data class Params(
        /** Форматируемый DocumentSnapshot, содержащий объект Mood */
        val snapshot: DocumentSnapshot,
    )
}