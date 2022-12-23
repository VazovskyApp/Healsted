package app.vazovsky.mypills.data.model.base

typealias Loading<T> = LoadableResult.Loading<T>
typealias Success<T> = LoadableResult.Success<T>
typealias Failure<T> = LoadableResult.Failure<T>

sealed class LoadableResult<R>(open val payload: R? = null) {
    data class Loading<R>(
        override val payload: R?,
    ) : LoadableResult<R>(payload)

    data class Success<R>(
        val value: R,
        override val payload: R? = null,
    ) : LoadableResult<R>(payload)

    data class Failure<R>(
        val throwable: Throwable,
        override val payload: R?,
        val error: ParsedError = throwable.parseError(),
    ) : LoadableResult<R>(payload)

    companion object {
        fun <R> loading(payload: R? = null): LoadableResult<R> = Loading(payload)
        fun <R> success(value: R, payload: R? = null): LoadableResult<R> = Success(value, payload)
        fun <R> failure(
            throwable: Throwable,
            payload: R? = null,
            error: ParsedError = throwable.parseError(),
        ): LoadableResult<R> = Failure(throwable, payload, error)
    }

    val isLoading: Boolean get() = this is Loading

    val isSuccess: Boolean get() = this is Success

    val isFailure: Boolean get() = this is Failure

    fun getOrNull(): R? = if (this is Success) value else null

    fun getOrDefault(default: () -> R): R = if (this is Success) value else default()

    fun getOrDefault(defaultValue: R): R = if (this is Success) value else defaultValue

    inline fun <C> fold(
        ifLoading: (payload: R?) -> C,
        ifFailure: (t: Throwable, payload: R?) -> C,
        ifSuccess: (R, payload: R?) -> C
    ): C = when (this) {
        is Loading -> ifLoading(payload)
        is Failure -> ifFailure(throwable, payload)
        is Success -> ifSuccess(value, payload)
    }

    inline fun <C> map(f: (R) -> C): LoadableResult<C> = when (this) {
        is Loading -> loading()
        is Failure -> failure(throwable)
        is Success -> success(f(value))
    }

    inline fun doOnComplete(operation: (LoadableResult<R>) -> Unit) {
        when (this) {
            is Failure -> operation(failure(throwable, payload))
            is Success -> operation(success(value))
            else -> Unit
        }
    }

    inline fun doOnLoading(operation: () -> Unit) {
        if (this is Loading) operation()
    }

    inline fun doOnSuccess(operation: (R) -> Unit) {
        if (this is Success) operation(value)
    }

    inline fun doOnFailure(operation: (ParsedError) -> Unit) {
        if (this is Failure) operation(error)
    }
}