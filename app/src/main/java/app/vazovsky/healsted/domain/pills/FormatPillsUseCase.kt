package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillsTabSlot
import app.vazovsky.healsted.domain.base.UseCaseUnary
import com.google.firebase.firestore.DocumentSnapshot
import java.time.OffsetDateTime
import javax.inject.Inject
import timber.log.Timber

class FormatPillsUseCase @Inject constructor(
    private val pillMapper: PillMapper,
) : UseCaseUnary<FormatPillsUseCase.Params, List<Pill>>() {

    override suspend fun execute(params: Params): List<Pill> {
        val listOfPills = mutableListOf<Pill>()
        params.snapshot.data?.let { data ->
            Timber.d("PILLS: $data")
            for ((key, value) in data) {
                val map = (value as Map<*, *>)
                val pill = pillMapper.fromMapToModel(map)
                listOfPills.add(pill)
            }
        }
        return when (params.slot) {
            PillsTabSlot.ACTIVE -> listOfPills.filter { it.endDate == null || it.endDate > OffsetDateTime.now() }
            PillsTabSlot.COMPLETED -> listOfPills.filter { it.endDate != null && it.endDate < OffsetDateTime.now() }
            PillsTabSlot.ALL -> listOfPills
            else -> listOfPills
        }
    }

    data class Params(
        val snapshot: DocumentSnapshot,
        val slot: PillsTabSlot?,
    )
}