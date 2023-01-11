package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orError
import app.vazovsky.healsted.extensions.toDataClass
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

/** Получение медикаментов, назначенных на текущий день */
class ParseSnapshotToTodayPillsUseCase @Inject constructor() :
    UseCaseUnary<ParseSnapshotToTodayPillsUseCase.Params, List<Pill>>() {

    override suspend fun execute(params: Params): List<Pill> {
        val listOfPills = mutableListOf<Pill>()
        params.snapshot.documents.forEach { snapshot ->
            val pill = snapshot.data?.toDataClass<Pill>().orError()
            listOfPills.add(pill)
        }
        /** TODO Сделать норм фильтр */
        return listOfPills.filter { it.startDate <= params.date }
    }

    data class Params(
        val snapshot: QuerySnapshot,
        val date: Timestamp,
    )
}