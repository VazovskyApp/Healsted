package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.presentation.pilleditor.UpdateResult
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject
import timber.log.Timber


class ChangeTimesPillUseCase @Inject constructor(
    private val pillMapper: PillMapper,
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<ChangeTimesPillUseCase.Params, UpdateResult>() {

    override suspend fun execute(params: Params): UpdateResult {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        val newHistory = mutableMapOf<LocalDateTime, LocalTime>()
        newHistory.putAll(params.pill.history)
        val localDateTime = LocalDateTime.of(params.date, params.time)
        val isDone = newHistory[localDateTime]
        if (isDone == null || isDone != params.time) {
            newHistory[localDateTime] = params.time
        } else if (isDone == params.time) {
            newHistory.remove(localDateTime, params.time)
        }
        val newPill = params.pill.copy(history = newHistory)

        return UpdateResult(
            firebaseProfileRepository.updateProfilePill(uid, pillMapper.fromModelToDocument(newPill)),
            newPill,
        )
    }

    data class Params(
        /** Обновленное лекарство */
        val pill: Pill,

        /** Дата для обновления */
        val date: LocalDate,

        /** Время для обновления */
        val time: LocalTime,
    )
}