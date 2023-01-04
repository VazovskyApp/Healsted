package app.vazovsky.healsted.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.dashboard.GetTodayPillsUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val destinations: DashboardDestinations,
    private val getTodayPillsUseCase: GetTodayPillsUseCase,
) : BaseViewModel() {

    /** Лекарства на сегодня */
    private val _todayPillsLiveData = MutableLiveData<LoadableResult<List<Pill>>>()
    val todayPillsLiveData: LiveData<LoadableResult<List<Pill>>> = _todayPillsLiveData

    /** Получение сегодняшних лекарств */
    fun getTodayPills() {
        _todayPillsLiveData.launchLoadData(
            getTodayPillsUseCase.executeFlow(UseCase.None)
        )
    }

    /** Открыть добавление лекарства */
    fun openAddPill() {
        navigate(destinations.addPill())
    }

    /** Открыть редактирование лекарства */
    fun openEditPill(pill: Pill) {
        navigate(destinations.editPill(pill))
    }
}