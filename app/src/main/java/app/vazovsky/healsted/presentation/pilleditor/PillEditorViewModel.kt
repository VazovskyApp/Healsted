package app.vazovsky.healsted.presentation.pilleditor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillTypeItem
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.pills.DeletePillUseCase
import app.vazovsky.healsted.domain.pills.GetPillTypesUseCase
import app.vazovsky.healsted.domain.pills.SavePillUseCase
import app.vazovsky.healsted.domain.pills.UpdatePillUseCase
import app.vazovsky.healsted.domain.roompills.DeleteRoomPillUseCase
import app.vazovsky.healsted.domain.roompills.InsertRoomPillUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import app.vazovsky.healsted.presentation.base.SingleLiveEvent
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PillEditorViewModel @Inject constructor(
    private val getPillTypesUseCase: GetPillTypesUseCase,
    private val savePillUseCase: SavePillUseCase,
    private val updatePillUseCase: UpdatePillUseCase,
    private val insertRoomPillUseCase: InsertRoomPillUseCase,
    private val deletePillUseCase: DeletePillUseCase,
    private val deleteRoomPillUseCase: DeleteRoomPillUseCase,
) : BaseViewModel() {

    /** Типы лекарств */
    private val _pillTypesLiveData = MutableLiveData<LoadableResult<List<PillTypeItem>>>()
    val pillTypesLiveData: LiveData<LoadableResult<List<PillTypeItem>>> = _pillTypesLiveData

    /** Добавление либо обновление лекарства */
    private val _updatePillLiveEvent = SingleLiveEvent<LoadableResult<UpdateResult>>()
    val updatePillLiveEvent: LiveData<LoadableResult<UpdateResult>> = _updatePillLiveEvent

    /** Удаление лекарства */
    private val _deletePillLiveEvent = SingleLiveEvent<LoadableResult<UpdateResult>>()
    val deletePillLiveEvent: LiveData<LoadableResult<UpdateResult>> = _deletePillLiveEvent

    /** Сохранение лекарства в локальную базу данных */
    private val _updateLocalPillLiveEvent = SingleLiveEvent<LoadableResult<Boolean>>()
    val updateLocalPillLiveEvent: LiveData<LoadableResult<Boolean>> = _updateLocalPillLiveEvent

    /** Удаление лекарства из локальной базы данных */
    private val _deleteLocalPillLiveEvent = SingleLiveEvent<LoadableResult<Boolean>>()
    val deleteLocalPillLiveEvent: LiveData<LoadableResult<Boolean>> = _deleteLocalPillLiveEvent

    /** Получение типов лекарств */
    fun getPillTypes() {
        _pillTypesLiveData.launchLoadData(
            getPillTypesUseCase.executeFlow(UseCase.None)
        )
    }

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
    fun deletePill(pill: Pill) {
        _deletePillLiveEvent.launchLoadData(
            deletePillUseCase.executeFlow(DeletePillUseCase.Params(pill))
        )
    }

    /** Сохранение лекарства в локальную базу данных */
    fun updateLocalPill(pill: Pill) {
        _updateLocalPillLiveEvent.launchLoadData(
            insertRoomPillUseCase.executeFlow(InsertRoomPillUseCase.Params(pill))
        )
    }

    /** Удаление лекарства из локальной базы данных */
    fun deleteLocalPill(pill: Pill) {
        _deleteLocalPillLiveEvent.launchLoadData(
            deleteRoomPillUseCase.executeFlow(DeleteRoomPillUseCase.Params(pill))
        )
    }
}

data class UpdateResult(
    val task: Task<Void>,
    val pill: Pill
)