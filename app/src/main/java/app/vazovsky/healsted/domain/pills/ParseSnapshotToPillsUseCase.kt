package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.firebase.model.PillDocument
import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillsTabSlot
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orError
import app.vazovsky.healsted.extensions.toDataClass
import com.google.firebase.firestore.QuerySnapshot
import java.time.LocalDate
import javax.inject.Inject

/** Парсинг QuerySnapshot в список фильтруемых лекарств */
class ParseSnapshotToPillsUseCase @Inject constructor(
    private val pillMapper: PillMapper,
) : UseCaseUnary<ParseSnapshotToPillsUseCase.Params, List<Pill>>() {

    override suspend fun execute(params: Params): List<Pill> {
        val listOfPills = mutableListOf<Pill>()
        params.snapshot.documents.forEach { snapshot ->
            val pillDocument = snapshot.data?.toDataClass<PillDocument>().orError()
            val pill = pillMapper.fromDocumentToModel(pillDocument)
            listOfPills.add(pill)
        }
        return when (params.slot) {
            PillsTabSlot.ACTIVE -> listOfPills.filter { it.endDate == null || it.endDate > LocalDate.now() }
            PillsTabSlot.COMPLETED -> listOfPills.filter { it.endDate != null && it.endDate < LocalDate.now() }
            PillsTabSlot.ALL -> listOfPills
            else -> listOfPills
        }
    }

    data class Params(
        /** Форматируемый QuerySnapshot со списком лекарств */
        val snapshot: QuerySnapshot,

        /** Выбранный слот для отображения */
        val slot: PillsTabSlot?,
    )
}