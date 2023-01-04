package app.vazovsky.healsted.core.repository

import app.vazovsky.healsted.core.model.DataState
import app.vazovsky.healsted.core.service.RemoteHelper
import com.google.gson.JsonArray
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class NotificationRepositoryImpl @Inject constructor(
    private val remoteHelper: RemoteHelper,
) : NotificationRepository {
    override fun getNotification(
        endPoint: String,
        authorization: String,
        deviceId: String
    ): Flow<DataState<JsonArray?>> = flow {
        try {
            val response = remoteHelper.getNotification(endPoint, authorization, deviceId)
            emit(DataState.Success(response!!))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }.catch {
        emit(DataState.Error(Exception(it)))
    }

    override fun clickedOnNotification(
        endPoint: String,
        authorization: String,
        id: String
    ): Flow<DataState<Boolean>> = flow {
        try {
            val res = remoteHelper.clickedOnNotification(endPoint, authorization, id)
            emit(DataState.Success(res?.get("result")?.asBoolean!!))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }.catch {
        emit(DataState.Error(Exception(it)))
    }
}