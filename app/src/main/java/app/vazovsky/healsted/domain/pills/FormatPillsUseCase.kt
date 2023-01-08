package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillsTabSlot
import app.vazovsky.healsted.domain.base.UseCaseUnary
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class FormatPillsUseCase @Inject constructor(
) : UseCaseUnary<FormatPillsUseCase.Params, List<Pill>>() {

    override suspend fun execute(params: Params): List<Pill> {
        val listOfPills = mutableListOf<Pill>()
        params.snapshot.documents.forEach { snapshot ->
            snapshot.toObject(Pill::class.java)?.let { pill -> listOfPills.add(pill) }
        }
        return when (params.slot) {
            PillsTabSlot.ACTIVE -> listOfPills.filter { it.endDate == null || it.endDate > Timestamp.now() }
            PillsTabSlot.COMPLETED -> listOfPills.filter { it.endDate != null && it.endDate < Timestamp.now() }
            PillsTabSlot.ALL -> listOfPills
            else -> listOfPills
        }
    }

    data class Params(
        val snapshot: QuerySnapshot,
        val slot: PillsTabSlot?,
    )
}