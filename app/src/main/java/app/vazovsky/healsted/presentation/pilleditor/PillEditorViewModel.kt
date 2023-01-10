package app.vazovsky.healsted.presentation.pilleditor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.pills.SavePillUseCase
import app.vazovsky.healsted.domain.pills.UpdatePillUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PillEditorViewModel @Inject constructor(
    private val savePillUseCase: SavePillUseCase,
    private val updatePillUseCase: UpdatePillUseCase,
) : BaseViewModel() {

    /** Добавление либо обновление лекарства */
    private val _updatePillLiveData = MutableLiveData<LoadableResult<Task<Void>>>()
    val updatePillLiveData: LiveData<LoadableResult<Task<Void>>> = _updatePillLiveData

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
}