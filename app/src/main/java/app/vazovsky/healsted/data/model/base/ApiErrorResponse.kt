package app.vazovsky.healsted.data.model.base

import com.google.gson.annotations.SerializedName

/** Данные об ошибке запроса */
data class ApiErrorResponse(
    /** Ошибка */
    @SerializedName("error") val error: Data?,
) {
    data class Data(
        /** Код ошибки */
        @SerializedName("code") val code: String?,

        /** Сообщение об ошибке */
        @SerializedName("message") val message: String?,
    )
}