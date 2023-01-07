package app.vazovsky.healsted.presentation.pills

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillsTab
import app.vazovsky.healsted.data.model.PillsTabSlot
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.pills.FormatPillsUseCase
import app.vazovsky.healsted.domain.pills.GetPillsUseCase
import app.vazovsky.healsted.domain.pills.GetTabsUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PillsViewModel @Inject constructor(
    private val destinations: PillsDestinations,
    private val getTabsUseCase: GetTabsUseCase,
    private val getPillsUseCase: GetPillsUseCase,
    private val formatPillsUseCase: FormatPillsUseCase,
) : BaseViewModel() {

    /** Табы */
    private val _tabsLiveData = MutableLiveData<LoadableResult<List<PillsTab>>>()
    val tabsLiveData: LiveData<LoadableResult<List<PillsTab>>> = _tabsLiveData

    /** Лекарства как DocumentSnapshot */
    private val _pillsDocumentSnapshotLiveData = MutableLiveData<LoadableResult<GetPillsUseCase.Result>>()
    val pillsDocumentSnapshotLiveData: LiveData<LoadableResult<GetPillsUseCase.Result>> = _pillsDocumentSnapshotLiveData

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
    fun getPillsDocumentSnapshot(slot: PillsTabSlot? = null) {
        _pillsDocumentSnapshotLiveData.launchLoadData(
            getPillsUseCase.executeFlow(GetPillsUseCase.Params(slot))
        )
    }

    fun getPills(snapshot: DocumentSnapshot, slot: PillsTabSlot? = null) {
        _pillsLiveData.launchLoadData(
            formatPillsUseCase.executeFlow(FormatPillsUseCase.Params(snapshot, slot))
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

    /** Нажатие на таб */
    fun onTabClick(tab: PillsTab) {
        val tabs = tabsLiveData.value?.getOrNull().orEmpty()
        if (tab.selected) {
            getPillsDocumentSnapshot()
            _tabsLiveData.postValue(LoadableResult.success(tabs.map { it.copy(selected = false) }))
        } else {
            getPillsDocumentSnapshot(tab.slot)
            _tabsLiveData.postValue(LoadableResult.success(tabs.map { it.copy(selected = it.slot == tab.slot) }))
        }
    }
}