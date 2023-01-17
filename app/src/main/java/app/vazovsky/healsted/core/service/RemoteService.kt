package app.vazovsky.healsted.core.service

import com.google.gson.JsonObject
import retrofit2.http.*

interface RemoteService {

    @GET
    suspend fun clickedOnNotification(
        @Url endPoint: String,
        @Header("authorization") authorization: String,
        @Query("notification_id") id: String,
    ): JsonObject?
}