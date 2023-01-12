package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orError
import app.vazovsky.healsted.extensions.toDataClass
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class ParseSnapshotToAllPillsUseCase @Inject constructor() : UseCaseUnary<ParseSnapshotToAllPillsUseCase.Params, List<Pill>>() {

    override suspend fun execute(params: Params): List<Pill> {
        val listOfPills = mutableListOf<Pill>()
        params.snapshot.documents.forEach { snapshot ->
            val pill = snapshot.data?.toDataClass<Pill>().orError()
            listOfPills.add(pill)
        }
        return listOfPills
    }

    data class Params(
        /** Форматируемый QuerySnapshot со списком Pill */
        val snapshot: QuerySnapshot,
    )
}