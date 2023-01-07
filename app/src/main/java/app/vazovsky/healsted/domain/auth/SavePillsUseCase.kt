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
import java.time.OffsetDateTime
import javax.inject.Inject

/** Добавление пустого списка таблеток для аккаунта  */
class SavePillsUseCase @Inject constructor(
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<UseCase.None, Task<Void>>() {

    override suspend fun execute(params: UseCase.None): Task<Void> {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        return firebaseProfileRepository.addProfilePills(
            uid, mapOf<String, Pill>(
                "Нурофен" to Pill(
                    id = "0",
                    name = "Нурофен",
                    type = PillType.CAPSULE,
                    times = null,
                    amount = 1F,
                    startDate = OffsetDateTime.now(),
                ),
                "Нурофен1" to Pill(
                    id = "0",
                    name = "Нурофен",
                    type = PillType.CAPSULE,
                    times = null,
                    amount = 1F,
                    startDate = OffsetDateTime.now().minusDays(2),
                ),
                "Нурофен2" to Pill(
                    id = "0",
                    name = "Нурофен",
                    type = PillType.CAPSULE,
                    times = null,
                    amount = 1F,
                    startDate = OffsetDateTime.now().minusDays(2),
                    endDate = OffsetDateTime.now().plusDays(20),
                )
            )
        )
    }
}