package app.vazovsky.healsted.data.model.base

import com.google.gson.annotations.SerializedName

data class ApiErrorResponse(
    @SerializedName("error") val error: Data?
) {
    data class Data(
        @SerializedName("code") val code: String?,
        @SerializedName("message") val message: String?,
    )
}