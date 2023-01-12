package app.vazovsky.healsted.domain.health

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import com.google.android.gms.tasks.Task
import javax.inject.Inject

/** Добавление атрибута мониторинга здоровья с текущим значением */
class AddMonitoringAttributeUseCase @Inject constructor(
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<AddMonitoringAttributeUseCase.Params, Task<Void>>() {

    override suspend fun execute(params: Params): Task<Void> {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        return firebaseProfileRepository.addProfileMonitoringAttribute(uid, params.monitoringAttribute)
    }

    data class Params(
        /** Атрибут мониторинга здоровья */
        val monitoringAttribute: MonitoringAttribute,
    )
}