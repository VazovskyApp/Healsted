package app.vazovsky.healsted.presentation.pilleditor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.base.LoadableResult
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
) : BaseViewModel() {

    /** Добавление либо обновление лекарства */
    private val _updatePillLiveData = MutableLiveData<LoadableResult<UpdateResult>>()
    val updatePillLiveData: LiveData<LoadableResult<UpdateResult>> = _updatePillLiveData

    /** Сохранение лекарства в локальную базу данных */
    private val _saveLocalPillLiveEvent = SingleLiveEvent<LoadableResult<Boolean>>()
    val saveLocalPillLiveEvent: LiveData<LoadableResult<Boolean>> = _saveLocalPillLiveEvent

    /** Добавление лекарства */
    fun addPill(pill: Pill) {
        _updatePillLiveData.launchLoadData(
            savePillUseCase.executeFlow(SavePillUseCase.Params(pill))
        )
    }

    /** Обновление лекарства */
    fun updatePill(pill: Pill) {
        _updatePillLiveData.launchLoadData(
            updatePillUseCase.executeFlow(UpdatePillUseCase.Params(pill))
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