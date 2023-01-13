package app.vazovsky.healsted.domain.health

import app.vazovsky.healsted.data.firebase.model.MonitoringAttributeDocument
import app.vazovsky.healsted.data.mapper.MonitoringAttributeMapper
import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orError
import app.vazovsky.healsted.extensions.toDataClass
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

/** Парсинг из QuerySnapshot в атрибут мониторинга здоровья с актуальным значением */
class ParseSnapshotToMonitoringUseCase @Inject constructor(
    private val monitoringAttributeMapper: MonitoringAttributeMapper,
) : UseCaseUnary<ParseSnapshotToMonitoringUseCase.Params, MonitoringAttribute>() {

    override suspend fun execute(params: Params): MonitoringAttribute {
        val history = mutableListOf<MonitoringAttribute>()
        params.snapshot.documents.forEach { snapshot ->
            val monitoringAttributeDocument = snapshot.data?.toDataClass<MonitoringAttributeDocument>().orError()
            val item = monitoringAttributeMapper.fromDocumentToModel(monitoringAttributeDocument)
            history.add(item)
        }
        return history.maxByOrNull { it.date }.orError()
    }

    data class Params(
        /** Форматируемый QuerySnapshot со списком MonitoringAttribute */
        val snapshot: QuerySnapshot,
    )
}