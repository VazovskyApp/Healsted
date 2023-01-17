package app.vazovsky.healsted.core.service

import com.google.gson.JsonObject
import javax.inject.Inject
import javax.inject.Named

class RemoteHelperImpl @Inject constructor(
    @Named("notificationRemoteService") private val remoteService: RemoteService,
) : RemoteHelper {

    override suspend fun clickedOnNotification(
        endPoint: String,
        authorization: String,
        id: String,
    ): JsonObject? = remoteService.clickedOnNotification(endPoint, authorization, id)
}