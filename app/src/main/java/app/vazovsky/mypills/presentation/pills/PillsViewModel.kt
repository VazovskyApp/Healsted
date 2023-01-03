package app.vazovsky.mypills.presentation.pills

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.mypills.data.model.Pill
import app.vazovsky.mypills.data.model.base.LoadableResult
import app.vazovsky.mypills.domain.base.UseCase
import app.vazovsky.mypills.domain.pills.GetActivePillsUseCase
import app.vazovsky.mypills.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PillsViewModel @Inject constructor(
    private val pillsDestinations: PillsDestinations,
    private val getActivePillsUseCase: GetActivePillsUseCase,
) : BaseViewModel() {

    /** Все лекарства */
    private val _pillsLiveData = MutableLiveData<LoadableResult<List<Pill>>>()
    val pillsLiveDataLiveData: LiveData<LoadableResult<List<Pill>>> = _pillsLiveData

    /** Получение данных обо всех лекарствах */
    fun getPills() {
        _pillsLiveData.launchLoadData(
            getActivePillsUseCase.executeFlow(UseCase.None)
        )
    }

    fun openAddPill() {
        navigate(pillsDestinations.addPill())
    }
}