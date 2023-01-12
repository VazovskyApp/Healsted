package app.vazovsky.healsted.domain.health

import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orError
import app.vazovsky.healsted.extensions.toDataClass
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

/** Парсинг QuerySnapshot в историю мониторинга здоровья */
class ParseSnapshotToMonitoringHistoryUseCase @Inject constructor() :
    UseCaseUnary<ParseSnapshotToMonitoringHistoryUseCase.Params, List<MonitoringAttribute>>() {

    override suspend fun execute(params: Params): List<MonitoringAttribute> {
        val history = mutableListOf<MonitoringAttribute>()
        params.snapshot.documents.forEach { snapshot ->
            val item = snapshot.data?.toDataClass<MonitoringAttribute>().orError()
            history.add(item)
        }
        return history
    }

    data class Params(
        /** Форматируемый QuerySnapshot со списком MonitoringAttribute */
        val snapshot: QuerySnapshot,
    )
}