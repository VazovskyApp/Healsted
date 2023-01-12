package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.presentation.pilleditor.UpdateResult
import javax.inject.Inject

/** Обновить данные о лекарстве */
class UpdatePillUseCase @Inject constructor(
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<UpdatePillUseCase.Params, UpdateResult>() {

    override suspend fun execute(params: Params): UpdateResult {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        return UpdateResult(
            firebaseProfileRepository.updateProfilePill(uid, params.pill),
            params.pill,
        )
    }

    data class Params(
        /** Обновленное лекарство */
        val pill: Pill,
    )
}