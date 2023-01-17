package app.vazovsky.healsted.core.repository

import app.vazovsky.healsted.core.model.DataState
import app.vazovsky.healsted.core.service.RemoteHelper
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class NotificationRepositoryImpl @Inject constructor(
    private val remoteHelper: RemoteHelper,
) : NotificationRepository {
    override fun clickedOnNotification(
        endPoint: String,
        authorization: String,
        id: String,
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