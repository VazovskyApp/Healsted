package app.vazovsky.healsted.core.repository

import app.vazovsky.healsted.core.model.DataState
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun clickedOnNotification(
        endPoint: String,
        authorization: String,
        id: String,
    ): Flow<DataState<Boolean>>
}