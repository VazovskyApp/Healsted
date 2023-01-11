package app.vazovsky.healsted.domain.health

import app.vazovsky.healsted.data.model.MonitoringAttribute
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orError
import app.vazovsky.healsted.extensions.toDataClass
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

/** Парсинг из снепшота в объект мониторинга */
class ParseSnapshotToMonitoringUseCase @Inject constructor() :
    UseCaseUnary<ParseSnapshotToMonitoringUseCase.Params, MonitoringAttribute>() {

    override suspend fun execute(params: Params): MonitoringAttribute {
        return params.snapshot.documents.first().data?.toDataClass<MonitoringAttribute>().orError()
    }

    data class Params(
        /** Форматируемый QuerySnapshot со списком Pill */
        val snapshot: QuerySnapshot,
    )
}