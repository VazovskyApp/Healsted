package app.vazovsky.healsted.domain.pills

import app.vazovsky.healsted.data.firebase.auth.FirebaseAuthRepository
import app.vazovsky.healsted.data.firebase.model.PillDocument
import app.vazovsky.healsted.data.mapper.PillMapper
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.repository.AuthRepository
import app.vazovsky.healsted.domain.base.UseCaseUnary
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.extensions.orError
import app.vazovsky.healsted.extensions.toDataClass
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

/** Парсинг QuerySnapshot в список всех лекарств */
class ParseSnapshotToAllPillsUseCase @Inject constructor(
    private val pillMapper: PillMapper,
    private val authRepository: AuthRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : UseCaseUnary<ParseSnapshotToAllPillsUseCase.Params, ParseSnapshotToAllPillsUseCase.Result>() {

    override suspend fun execute(params: Params): Result {
        val uid = firebaseAuthRepository.getCurrentUserUid() ?: authRepository.getCurrentUserUid().orDefault()

        val listOfPills = mutableListOf<Pill>()
        params.snapshot.documents.forEach { snapshot ->
            val pillDocument = snapshot.data?.toDataClass<PillDocument>().orError()
            val pill = pillMapper.fromDocumentToModel(pillDocument)
            listOfPills.add(pill)
        }
        return Result(
            pills = listOfPills,
            uid = uid
        )
    }

    data class Params(
        /** Форматируемый QuerySnapshot со списком лекарств */
        val snapshot: QuerySnapshot,
    )

    data class Result(
        val pills: List<Pill>,
        val uid: String,
    )
}