package app.vazovsky.mypills.domain.pills

import android.os.Build
import androidx.annotation.RequiresApi
import app.vazovsky.mypills.data.model.Pill
import app.vazovsky.mypills.data.repository.PillsRepository
import app.vazovsky.mypills.domain.base.UseCase
import app.vazovsky.mypills.domain.base.UseCaseUnary
import java.time.LocalDate
import javax.inject.Inject

/** Получение активных лекарств */
class GetActivePillsUseCase @Inject constructor(
    private val pillsRepository: PillsRepository,
) : UseCaseUnary<UseCase.None, List<Pill>>() {

    //TODO поправить
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun execute(params: UseCase.None): List<Pill> {
        return pillsRepository.getPills().filter { it.endDate == null || it.endDate > LocalDate.now() }
    }
}