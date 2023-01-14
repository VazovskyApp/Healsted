package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.firebase.model.PillDocument
import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.TodayPillItem
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orError
import app.vazovsky.healsted.extensions.toDataClass
import com.google.firebase.firestore.QuerySnapshot
import java.time.LocalDate
import javax.inject.Inject

/** Парсинг QuerySnapshot в список лекарств на сегодняшний день */
class ParseSnapshotToTodayPillsUseCase @Inject constructor(
    private val pillMapper: PillMapper,
) : UseCaseUnary<ParseSnapshotToTodayPillsUseCase.Params, List<TodayPillItem>>() {

    override suspend fun execute(params: Params): List<TodayPillItem> {
        val pills = mutableListOf<Pill>()
        val resultPills = mutableListOf<TodayPillItem>()
        params.snapshot.documents.forEach { snapshot ->
            val pillDocument = snapshot.data?.toDataClass<PillDocument>().orError()
            val pill = pillMapper.fromDocumentToModel(pillDocument)
            pills.add(pill)
        }
        val filteredPills = pills.filter {
            it.startDate <= params.date && if (it.endDate != null) {
                it.endDate >= params.date
            } else true
        }
        filteredPills.forEach { pill ->
            pill.times.forEach { (_, time) ->
                resultPills.add(TodayPillItem(pill = pill, date = params.date, time = time))
            }
        }

        return resultPills.sortedBy { it.time }
    }

    data class Params(
        /** Форматируемый QuerySnapshot со списком лекарств */
        val snapshot: QuerySnapshot,

        /** Дата, по которой нужно получить лекарства */
        val date: LocalDate,
    )
}