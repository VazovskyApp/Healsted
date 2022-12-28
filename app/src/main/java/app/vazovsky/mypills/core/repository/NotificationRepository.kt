package app.vazovsky.mypills.core.repository

import app.vazovsky.mypills.core.model.DataState
import com.google.gson.JsonArray
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotification(
        endPoint: String,
        authorization: String,
        deviceId: String
    ): Flow<DataState<JsonArray?>>

    fun clickedOnNotification(
        endPoint: String,
        authorization: String,
        id: String
    ): Flow<DataState<Boolean>>
}