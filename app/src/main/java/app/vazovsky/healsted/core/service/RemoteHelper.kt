package app.vazovsky.healsted.core.service

import com.google.gson.JsonArray
import com.google.gson.JsonObject

interface RemoteHelper {
    suspend fun getNotification(
        endPoint: String,
        authorization: String,
        deviceId: String,
    ): JsonArray?

    suspend fun clickedOnNotification(
        endPoint: String,
        authorization: String,
        id: String,
    ): JsonObject?
}