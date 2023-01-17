package app.vazovsky.healsted.core.service

import com.google.gson.JsonObject

interface RemoteHelper {

    suspend fun clickedOnNotification(
        endPoint: String,
        authorization: String,
        id: String,
    ): JsonObject?
}