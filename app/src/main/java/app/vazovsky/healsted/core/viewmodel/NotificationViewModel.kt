package app.vazovsky.healsted.core.viewmodel

import androidx.lifecycle.ViewModel
import app.vazovsky.healsted.core.model.DataState
import app.vazovsky.healsted.core.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    /** Нажатие на уведомление */
    fun clickedOnNotification(
        endPoint: String,
        token: String,
        id: String
    ): Flow<DataState<Boolean>> = notificationRepository.clickedOnNotification(
        endPoint = endPoint,
        authorization = token,
        id = id
    ).shareIn(CoroutineScope(Dispatchers.IO), SharingStarted.Eagerly, 1)
}