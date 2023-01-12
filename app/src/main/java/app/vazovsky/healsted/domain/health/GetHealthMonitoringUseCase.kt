package app.vazovsky.healsted.domain.health

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.profile.FirebaseProfileRepository
import app.vazovsky.healsted.data.model.MonitoringType
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

/** Получение данных о мониторинге здоровья в виде QuerySnapshot */
class GetHealthMonitoringUseCase @Inject constructor(
    private val firebaseProfileRepository: FirebaseProfileRepository,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<GetHealthMonitoringUseCase.Params, Task<QuerySnapshot>>() {
    override suspend fun execute(params: Params): Task<QuerySnapshot> {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        return when (params.type) {
            MonitoringType.WEIGHT -> firebaseProfileRepository.fetchProfileWeight(uid)
            MonitoringType.HEIGHT -> firebaseProfileRepository.fetchProfileHeight(uid)
            MonitoringType.TEMPERATURE -> firebaseProfileRepository.fetchProfileTemperature(uid)
            MonitoringType.BLOOD_PRESSURE -> firebaseProfileRepository.fetchProfileBloodPressure(uid)
        }
    }

    data class Params(
        /** Параметр, который нужно загрузить */
        val type: MonitoringType,
    )
}