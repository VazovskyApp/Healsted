package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.firebase.model.PillDocument
import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.TodayPillItem
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orError
import app.vazovsky.healsted.extensions.toDataClass
import app.vazovsky.healsted.managers.DateFormatter
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class ParseSnapshotToPillsHistoryUseCase @Inject constructor(
    private val pillMapper: PillMapper,
    private val dateFormatter: DateFormatter,
) : UseCaseUnary<ParseSnapshotToPillsHistoryUseCase.Params, List<TodayPillItem>>() {

    override suspend fun execute(params: Params): List<TodayPillItem> {
        val pills = mutableListOf<Pill>()
        val resultPills = mutableListOf<TodayPillItem>()
        params.snapshot.documents.forEach { snapshot ->
            val pillDocument = snapshot.data?.toDataClass<PillDocument>().orError()
            val pill = pillMapper.fromDocumentToModel(pillDocument)
            pills.add(pill)
        }
        pills.forEach { pill ->
            pill.history.forEach { (dateTime, time) ->
                resultPills.add(TodayPillItem(pill = pill, date = dateTime.toLocalDate(), time = time))
            }
        }

        return resultPills.sortedBy { it.time }
    }

    data class Params(
        /** Форматируемый QuerySnapshot со списком лекарств */
        val snapshot: QuerySnapshot,
    )
}