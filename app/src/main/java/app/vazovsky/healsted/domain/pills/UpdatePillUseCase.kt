package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.presentation.pilleditor.UpdateResult
import javax.inject.Inject

/** Обновление данные о лекарстве */
class UpdatePillUseCase @Inject constructor(
    private val pillMapper: PillMapper,
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<UpdatePillUseCase.Params, UpdateResult>() {

    override suspend fun execute(params: Params): UpdateResult {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        return UpdateResult(
            task = firebaseProfileRepository.updateProfilePill(uid, pillMapper.fromModelToDocument(params.pill)),
            pill = params.pill,
            uid = uid,
        )
    }

    data class Params(
        /** Обновленное лекарство */
        val pill: Pill,
    )
}