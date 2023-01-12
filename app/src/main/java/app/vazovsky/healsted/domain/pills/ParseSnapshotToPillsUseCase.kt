package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillsTabSlot
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orError
import app.vazovsky.healsted.extensions.toDataClass
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

/** Парсинг QuerySnapshot в список фильтруемых лекарств */
class ParseSnapshotToPillsUseCase @Inject constructor() : UseCaseUnary<ParseSnapshotToPillsUseCase.Params, List<Pill>>() {

    override suspend fun execute(params: Params): List<Pill> {
        val listOfPills = mutableListOf<Pill>()
        params.snapshot.documents.forEach { snapshot ->
            val pill = snapshot.data?.toDataClass<Pill>().orError()
            listOfPills.add(pill)
        }
        return when (params.slot) {
            PillsTabSlot.ACTIVE -> listOfPills.filter { it.endDate == null || it.endDate > Timestamp.now() }
            PillsTabSlot.COMPLETED -> listOfPills.filter { it.endDate != null && it.endDate < Timestamp.now() }
            PillsTabSlot.ALL -> listOfPills
            else -> listOfPills
        }
    }

    data class Params(
        /** Форматируемый QuerySnapshot со списком Pill */
        val snapshot: QuerySnapshot,

        /** Выбранный слот для отображения */
        val slot: PillsTabSlot?,
    )
}