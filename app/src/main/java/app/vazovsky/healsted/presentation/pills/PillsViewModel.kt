package app.vazovsky.healsted.presentation.pills

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillsTab
import app.vazovsky.healsted.data.model.PillsTabSlot
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.data.room.entity.PillEntity
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.pills.GetLocalPillsUseCase
import app.vazovsky.healsted.domain.pills.GetPillsUseCase
import app.vazovsky.healsted.domain.pills.GetTabsUseCase
import app.vazovsky.healsted.domain.pills.ParseSnapshotToPillsUseCase
import app.vazovsky.healsted.presentation.base.BaseViewModel
import app.vazovsky.healsted.presentation.base.SingleLiveEvent
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PillsViewModel @Inject constructor(
    private val destinations: PillsDestinations,
    private val getTabsUseCase: GetTabsUseCase,
    private val getPillsUseCase: GetPillsUseCase,
    private val parseSnapshotToPillsUseCase: ParseSnapshotToPillsUseCase,
    private val getLocalPillsUseCase: GetLocalPillsUseCase,
) : BaseViewModel() {

    var selectedPillTab: PillsTabSlot = PillsTabSlot.ALL

    /** Табы */
    private val _tabsLiveData = MutableLiveData<LoadableResult<List<PillsTab>>>()
    val tabsLiveData: LiveData<LoadableResult<List<PillsTab>>> = _tabsLiveData

    /** Лекарства как DocumentSnapshot */
    private val _pillsSnapshotLiveData = MutableLiveData<LoadableResult<GetPillsUseCase.Result>>()
    val pillsSnapshotLiveData: LiveData<LoadableResult<GetPillsUseCase.Result>> = _pillsSnapshotLiveData

    /** Все лекарства */
    private val _pillsLiveData = MutableLiveData<LoadableResult<List<Pill>>>()
    val pillsLiveData: LiveData<LoadableResult<List<Pill>>> = _pillsLiveData

    /** Лекарства из базы данных */
    private val _localPillsLiveEvent = SingleLiveEvent<LoadableResult<List<PillEntity>>>()
    val localPillsLiveEvent: LiveData<LoadableResult<List<PillEntity>>> = _localPillsLiveEvent

    /** Получение табов */
    fun getTabs() {
        _tabsLiveData.launchLoadData(
            getTabsUseCase.executeFlow(UseCase.None)
        )
    }

    fun getPillsSnapshot(slot: PillsTabSlot? = null) {
        _pillsSnapshotLiveData.launchLoadData(
            getPillsUseCase.executeFlow(GetPillsUseCase.Params(slot))
        )
    }

    /** Получение данных о лекарствах */
    fun getPills(snapshot: QuerySnapshot, slot: PillsTabSlot? = null) {
        _pillsLiveData.launchLoadData(
            parseSnapshotToPillsUseCase.executeFlow(ParseSnapshotToPillsUseCase.Params(snapshot, slot))
        )
    }

    /** Получение лекарств из локальной базы данных */
    fun getLocalPills() {
        viewModelScope.launch {
            try {
                getLocalPillsUseCase.executeFlow(UseCase.None).collect {

                    it.doOnSuccess { flow ->
                        flow.collect { list ->
                            _localPillsLiveEvent.postValue(LoadableResult.success(list))
                        }
                    }
                }
            } catch (e: Exception) {
                _localPillsLiveEvent.postValue(LoadableResult.failure(Throwable()))
            }
        }
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
            getPillsSnapshot()
            _tabsLiveData.postValue(LoadableResult.success(tabs.map { it.copy(selected = false) }))
        } else {
            getPillsSnapshot(tab.slot)
            _tabsLiveData.postValue(LoadableResult.success(tabs.map { it.copy(selected = it.slot == tab.slot) }))
        }
    }
}