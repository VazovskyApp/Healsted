package app.vazovsky.mypills.presentation.pills

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.mypills.data.model.Pill
import app.vazovsky.mypills.data.model.PillsTab
import app.vazovsky.mypills.data.model.PillsTabSlot
import app.vazovsky.mypills.data.model.base.LoadableResult
import app.vazovsky.mypills.domain.base.UseCase
import app.vazovsky.mypills.domain.pills.GetPillsUseCase
import app.vazovsky.mypills.domain.pills.GetTabsUseCase
import app.vazovsky.mypills.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PillsViewModel @Inject constructor(
    private val pillsDestinations: PillsDestinations,
    private val getTabsUseCase: GetTabsUseCase,
    private val getPillsUseCase: GetPillsUseCase,
) : BaseViewModel() {

    /** Табы */
    private val _tabsLiveData = MutableLiveData<LoadableResult<List<PillsTab>>>()
    val tabsLiveData: LiveData<LoadableResult<List<PillsTab>>> = _tabsLiveData

    /** Все лекарства */
    private val _pillsLiveData = MutableLiveData<LoadableResult<List<Pill>>>()
    val pillsLiveData: LiveData<LoadableResult<List<Pill>>> = _pillsLiveData

    /** Получение табов */
    fun getTabs() {
        _tabsLiveData.launchLoadData(
            getTabsUseCase.executeFlow(UseCase.None)
        )
    }

    /** Получение данных о лекарствах */
    fun getPills(slot: PillsTabSlot? = null) {
        _pillsLiveData.launchLoadData(
            getPillsUseCase.executeFlow(GetPillsUseCase.Params(slot))
        )
    }

    fun openAddPill() {
        navigate(pillsDestinations.addPill())
    }

    fun onTabClick(tab: PillsTab) {
        val tabs = tabsLiveData.value?.getOrNull().orEmpty()
        if (tab.selected) {
            getPills()
            _tabsLiveData.postValue(LoadableResult.success(tabs.map { it.copy(selected = false) }))
        } else {
            getPills(tab.slot)
            _tabsLiveData.postValue(LoadableResult.success(tabs.map { it.copy(selected = it.slot == tab.slot) }))
        }
    }
}