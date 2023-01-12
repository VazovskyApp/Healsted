package app.vazovsky.healsted.domain.health

import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orError
import app.vazovsky.healsted.extensions.toDataClass
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

/** Парсинг из QuerySnapshot в атрибут мониторинга здоровья с актуальным значением */
class ParseSnapshotToMonitoringUseCase @Inject constructor() :
    UseCaseUnary<ParseSnapshotToMonitoringUseCase.Params, MonitoringAttribute>() {

    override suspend fun execute(params: Params): MonitoringAttribute {
        val history = mutableListOf<MonitoringAttribute>()
        params.snapshot.documents.forEach { snapshot ->
            val item = snapshot.data?.toDataClass<MonitoringAttribute>().orError()
            history.add(item)
        }
        return history.maxByOrNull { it.date }.orError()
    }

    data class Params(
        /** Форматируемый QuerySnapshot со списком MonitoringAttribute */
        val snapshot: QuerySnapshot,
    )
}