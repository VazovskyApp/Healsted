package app.vazovsky.healsted.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.healsted.data.model.Account
import app.vazovsky.healsted.data.model.Mood
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.domain.base.UseCase
import app.vazovsky.healsted.domain.mood.GetTodayMoodUseCase
import app.vazovsky.healsted.domain.mood.ParseSnapshotToMoodUseCase
import app.vazovsky.healsted.domain.mood.UpdateMoodUseCase
import app.vazovsky.healsted.domain.pills.GetTodayPillsUseCase
import app.vazovsky.healsted.domain.pills.ParseSnapshotToTodayPillsUseCase
import app.vazovsky.healsted.domain.profile.GetProfileUseCase
import app.vazovsky.healsted.domain.profile.ParseSnapshotToProfileUseCase
import app.vazovsky.healsted.extensions.toStartOfDayTimestamp
import app.vazovsky.healsted.presentation.base.BaseViewModel
import app.vazovsky.healsted.presentation.base.SingleLiveEvent
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val destinations: DashboardDestinations,
    private val getProfileUseCase: GetProfileUseCase,
    private val parseSnapshotToProfileUseCase: ParseSnapshotToProfileUseCase,
    private val getTodayPillsUseCase: GetTodayPillsUseCase,
    private val parseSnapshotToTodayPillsUseCase: ParseSnapshotToTodayPillsUseCase,
    private val getTodayMoodUseCase: GetTodayMoodUseCase,
    private val parseSnapshotToMoodUseCase: ParseSnapshotToMoodUseCase,
    private val updateMoodUseCase: UpdateMoodUseCase,
) : BaseViewModel() {

    var selectedDate = LocalDate.now().toStartOfDayTimestamp()

    /** Данные об аккаунте в виде DocumentSnapshot*/
    private val _profileSnapshotLiveData = MutableLiveData<LoadableResult<Task<DocumentSnapshot>>>()
    val profileSnapshotLiveData: LiveData<LoadableResult<Task<DocumentSnapshot>>> = _profileSnapshotLiveData

    /** Данные об аккаунте */
    private val _profileLiveData = MutableLiveData<LoadableResult<Account>>()
    val profileLiveData: LiveData<LoadableResult<Account>> = _profileLiveData

    /** Лекарства на сегодня */
    private val _todayPillsLiveData = MutableLiveData<LoadableResult<List<Pill>>>()
    val todayPillsLiveData: LiveData<LoadableResult<List<Pill>>> = _todayPillsLiveData

    /** Лекарства как DocumentSnapshot */
    private val _todayPillsSnapshotLiveData = MutableLiveData<LoadableResult<GetTodayPillsUseCase.Result>>()
    val todayPillsSnapshotLiveData: LiveData<LoadableResult<GetTodayPillsUseCase.Result>> = _todayPillsSnapshotLiveData

    /** Настроение на сегодня */
    private val _todayMoodSnapshotLiveData = MutableLiveData<LoadableResult<Task<DocumentSnapshot>>>()
    val todayMoodSnapshotLiveData: LiveData<LoadableResult<Task<DocumentSnapshot>>> = _todayMoodSnapshotLiveData

    /** Обновление настроения */
    private val _updateMoodLiveEvent = SingleLiveEvent<LoadableResult<Task<Void>>>()
    val updateMoodLiveEvent: LiveData<LoadableResult<Task<Void>>> = _updateMoodLiveEvent

    /** Настроение на сегодня */
    private val _todayMoodLiveData = MutableLiveData<LoadableResult<Mood>>()
    val todayMoodLiveData: LiveData<LoadableResult<Mood>> = _todayMoodLiveData

    /** Получение профиля в виде DocumentSnapshot */
    fun getProfileSnapshot() {
        _profileSnapshotLiveData.launchLoadData(getProfileUseCase.executeFlow(UseCase.None))
    }

    /** Получение профиля из DocumentSnapshot */
    fun getProfile(snapshot: DocumentSnapshot) {
        _profileLiveData.launchLoadData(parseSnapshotToProfileUseCase.executeFlow(ParseSnapshotToProfileUseCase.Params(snapshot)))
    }

    /** Получение лекарств на сегодня в виде QuerySnapshot */
    fun getTodayPillsSnapshot(date: Timestamp) {
        _todayPillsSnapshotLiveData.launchLoadData(
            getTodayPillsUseCase.executeFlow(GetTodayPillsUseCase.Params(date))
        )
    }

    /** Получение лекарств на сегодня из QuerySnapshot */
    fun getTodayPills(
        snapshot: QuerySnapshot,
        date: Timestamp,
    ) {
        _todayPillsLiveData.launchLoadData(
            parseSnapshotToTodayPillsUseCase.executeFlow(
                ParseSnapshotToTodayPillsUseCase.Params(snapshot, date)
            )
        )
    }

    /** Получение сегодняшнего настроения в виде DocumentSnapshot */
    fun getTodayMoodSnapshot() {
        _todayMoodSnapshotLiveData.launchLoadData(getTodayMoodUseCase.executeFlow(UseCase.None))
    }

    /** Получение сегодняшнего настроения из DocumentSnapshot */
    fun getTodayMood(snapshot: DocumentSnapshot) {
        _todayMoodLiveData.launchLoadData(
            parseSnapshotToMoodUseCase.executeFlow(
                ParseSnapshotToMoodUseCase.Params(snapshot)
            )
        )
    }

    /** Обновление сегодняшнего настроения */
    fun updateMood(mood: Mood) {
        _updateMoodLiveEvent.launchLoadData(updateMoodUseCase.executeFlow(UpdateMoodUseCase.Params(mood)))
    }

    /** Открыть добавление лекарства TODO не факт, что это нужно на данном экране */
    fun openAddPill() {
        navigate(destinations.addPill())
    }

    /** Открыть редактирование лекарства */
    fun openEditPill(pill: Pill) {
        navigate(destinations.editPill(pill))
    }
}