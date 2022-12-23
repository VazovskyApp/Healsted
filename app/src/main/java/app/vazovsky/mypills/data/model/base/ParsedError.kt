package app.vazovsky.mypills.data.model.base

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException
import timber.log.Timber

sealed class ParsedError(
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String
)

class NetworkError(code: String, message: String) : ParsedError(code, message)
class GeneralError(code: String, message: String) : ParsedError(code, message)
class ApiError(code: String, message: String) : ParsedError(code, message)

var DEFAULT_ERROR_MESSAGE: String = "LOL"
const val DEFAULT_ERROR_CODE = "TEXT_ERROR"

fun ApiErrorResponse.toParsedError(): ParsedError {
    return ApiError(
        code = error?.code ?: DEFAULT_ERROR_CODE,
        message = error?.message ?: DEFAULT_ERROR_MESSAGE,
    )
}

fun Throwable.parseError(): ParsedError {
    Timber.e(this)
    val code = DEFAULT_ERROR_CODE
    val message = DEFAULT_ERROR_MESSAGE

    return when {
        isNetworkError -> NetworkError(code, message)
        this is HttpException -> {
            val body = response()?.errorBody()
            val gson = GsonBuilder().create()
            var error: ParsedError? = null
            try {
                val apiError = gson.fromJson(body?.string(), ApiErrorResponse::class.java)
                response()?.code()?.let { error = apiError?.toParsedError() }
            } catch (ioEx: IOException) {
                Timber.e(ioEx)
            } catch (isEx: IllegalStateException) {
                Timber.e(isEx)
            } catch (jsEx: JsonSyntaxException) {
                Timber.e(jsEx)
            }
            error ?: GeneralError(code, message)
        }

        else -> {
            GeneralError(code, message)
        }
    }
}

val Throwable.isNetworkError: Boolean
    get() = when (this) {
        is ConnectException,
        is UnknownHostException,
        is SocketTimeoutException -> true

        else -> false
    }