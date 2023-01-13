package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.presentation.pilleditor.UpdateResult
import java.time.LocalTime
import javax.inject.Inject


class ChangeTimesPillUseCase @Inject constructor(
    private val pillMapper: PillMapper,
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<ChangeTimesPillUseCase.Params, UpdateResult>() {

    override suspend fun execute(params: Params): UpdateResult {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        val newTimes = mutableMapOf<LocalTime, Boolean>()
        params.pill.times.map {
            newTimes.put(it.key, if (it.key == params.time) !it.value else it.value)
        }
        val newPill = params.pill.copy(times = newTimes)

        return UpdateResult(
            firebaseProfileRepository.updateProfilePill(uid, pillMapper.fromModelToDocument(newPill)),
            newPill,
        )
    }

    data class Params(
        /** Обновленное лекарство */
        val pill: Pill,

        /** Время для обновления */
        val time: LocalTime,
    )
}