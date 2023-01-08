package app.vazovsky.healsted.domain.auth

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillType
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import java.time.OffsetDateTime
import javax.inject.Inject

/** Добавление пустого списка таблеток для аккаунта  */
class SavePillUseCase @Inject constructor(
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<UseCase.None, Task<Void>>() {

    override suspend fun execute(params: UseCase.None): Task<Void> {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        return firebaseProfileRepository.addProfilePill(
            uid = uid,
            pill = Pill(
                id = "0",
                name = "Пример",
                type = PillType.CAPSULE,
                times = null,
                amount = 1F,
                startDate = Timestamp.now(),
            ),
        )
    }
}