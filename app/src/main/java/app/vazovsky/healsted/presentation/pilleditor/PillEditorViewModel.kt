package app.vazovsky.healsted.presentation.pilleditor

import androidx.lifecycle.LiveData
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.pills.DeletePillUseCase
import app.vazovsky.healsted.domain.pills.InsertPillToDatabaseUseCase
import app.vazovsky.healsted.domain.pills.SavePillUseCase
import app.vazovsky.healsted.domain.pills.UpdatePillUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import app.vazovsky.healsted.presentation.base.SingleLiveEvent
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PillEditorViewModel @Inject constructor(
    private val savePillUseCase: SavePillUseCase,
    private val updatePillUseCase: UpdatePillUseCase,
    private val insertPillToDatabaseUseCase: InsertPillToDatabaseUseCase,
    private val deletePillUseCase: DeletePillUseCase,
) : BaseViewModel() {

    /** Добавление либо обновление лекарства */
    private val _updatePillLiveEvent = SingleLiveEvent<LoadableResult<UpdateResult>>()
    val updatePillLiveEvent: LiveData<LoadableResult<UpdateResult>> = _updatePillLiveEvent

    /** Удаление лекарства */
    private val _deletePillLiveEvent = SingleLiveEvent<LoadableResult<Task<Void>>>()
    val deletePillLiveEvent: LiveData<LoadableResult<Task<Void>>> = _deletePillLiveEvent

    /** Сохранение лекарства в локальную базу данных */
    private val _saveLocalPillLiveEvent = SingleLiveEvent<LoadableResult<Boolean>>()
    val saveLocalPillLiveEvent: LiveData<LoadableResult<Boolean>> = _saveLocalPillLiveEvent

    /** Добавление лекарства */
    fun addPill(pill: Pill) {
        _updatePillLiveEvent.launchLoadData(
            savePillUseCase.executeFlow(SavePillUseCase.Params(pill))
        )
    }

    /** Обновление лекарства */
    fun updatePill(pill: Pill) {
        _updatePillLiveEvent.launchLoadData(
            updatePillUseCase.executeFlow(UpdatePillUseCase.Params(pill))
        )
    }

    /** Удаление лекарства */
    fun deletePill(pill: Pill){
        _deletePillLiveEvent.launchLoadData(
            deletePillUseCase.executeFlow(DeletePillUseCase.Params(pill))
        )
    }

    /** Сохранение лекарства в локальную базу данных */
    fun savePill(pill: Pill) {
        _saveLocalPillLiveEvent.launchLoadData(
            insertPillToDatabaseUseCase.executeFlow(InsertPillToDatabaseUseCase.Params(pill))
        )
    }
}

data class UpdateResult(
    val task: Task<Void>,
    val pill: Pill
)