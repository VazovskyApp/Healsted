package app.vazovsky.healsted.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.annotation.LayoutRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.base.LoadableResult
import app.vazovsky.healsted.data.model.base.NetworkError
import app.vazovsky.healsted.data.model.base.ParsedError
import com.airbnb.lottie.LottieAnimationView

class StateViewFlipper(context: Context, attrs: AttributeSet? = null) : ViewFlipper(context, attrs) {

    enum class State(val displayedChild: Int) {
        LOADING(0),
        ERROR(1),
        DATA(2),
        CUSTOM(3)
    }

    private var state = State.LOADING

    private var loadingView: View
    private lateinit var errorView: View

    private val buttonError: Button by lazy { errorView.findViewById(R.id.buttonError) }
    private val animationViewError: LottieAnimationView? by lazy { errorView.findViewById(R.id.animationViewError) }
    private val textErrorTitle: TextView? by lazy { errorView.findViewById(R.id.textErrorTitle) }
    private val textErrorDescription: TextView? by lazy { errorView.findViewById(R.id.textErrorDescription) }

    private val disabledStates = mutableListOf<State>()

    init {
        val layoutInflater = LayoutInflater.from(context)
        val layoutResProvider = LayoutResProvider(context, attrs)

        loadingView = layoutInflater.inflate(layoutResProvider.loadingRes, this, false)
        addView(loadingView)

        errorView = layoutInflater.inflate(layoutResProvider.errorRes, this, false)
        addView(errorView)
    }

    fun <T> setStateFromResult(loadableResult: LoadableResult<T>, useApiErrorMessage: Boolean = false) {
        when (loadableResult) {
            is LoadableResult.Loading -> setStateLoading()
            is LoadableResult.Success -> setStateData()
            is LoadableResult.Failure -> setStateError(loadableResult.error, useApiErrorMessage)
        }
    }

    fun setRetryMethod(retry: () -> Unit) {
        buttonError.setOnClickListener { retry.invoke() }
    }

    fun hideErrorIcon() {
        animationViewError?.isVisible = false
    }

    fun setCustomState() {
        changeState(State.CUSTOM)
        runAnimationAndStopOthers()
    }

    fun currentState() = state

    /** Метод деактивирует определенное состояние и не обрабатывает его в changeState() */
    fun disableState(vararg states: State) {
        for (state in states) {
            if (stateIsDisabled(state)) continue
            disabledStates.add(state)
            getChildAt(state.displayedChild)?.isVisible = false
        }
    }

    fun setLoadingView(@LayoutRes layout: Int) {
        removeView(loadingView)
        loadingView = LayoutInflater.from(context).inflate(layout, this, false)
        addView(loadingView, 0)
        changeState(state)
    }

    fun changeState(newState: State) {
        if (stateIsDisabled(newState)) return
        if (state != newState || displayedChild != newState.displayedChild) {
            state = newState
            displayedChild = newState.displayedChild
        }
    }

    private fun setStateLoading() {
        changeState(State.LOADING)
        runAnimationAndStopOthers()
    }

    private fun setStateError(error: ParsedError, useApiErrorMessage: Boolean) {
        changeState(State.ERROR)

        when (error) {
            is NetworkError -> setStateNetworkError()
            else -> setGeneralError(error, useApiErrorMessage)
        }
        runAnimationAndStopOthers(animationViewError)
    }

    private fun setStateData() {
        changeState(State.DATA)
        runAnimationAndStopOthers()
    }

    /**
     * @param animationView - Запускает эту анимацию и останавливает анимации у других состояний. Если null,
     * то останавливает все анимации
     */
    private fun runAnimationAndStopOthers(animationView: LottieAnimationView? = null) {
        animationViewError?.cancelAnimation()
        animationView?.playAnimation()
    }

    /**
     * Ошибка "Что-то с интернетом"
     */
    private fun setStateNetworkError() {
        setErrorStateContent(
            titleRes = R.string.error_no_network_title,
            descriptionRes = R.string.error_no_network_description,
            errorRes = R.raw.something_wrong,
        )
    }

    /**
     * Ошибка "Что-то не так"
     */
    private fun setGeneralError(error: ParsedError, useApiErrorMessage: Boolean) {
        setErrorStateContent(
            titleRes = R.string.error_something_wrong_title,
            description = if (useApiErrorMessage) {
                error.message
            } else {
                context.getString(R.string.error_something_wrong_description)
            },
            errorRes = R.raw.something_wrong,
        )
    }

    private fun setErrorStateContent(@StringRes titleRes: Int, @StringRes descriptionRes: Int, @RawRes errorRes: Int) {
        textErrorTitle?.setText(titleRes)
        textErrorDescription?.setText(descriptionRes)
        animationViewError?.setAnimation(errorRes)
    }

    private fun setErrorStateContent(@StringRes titleRes: Int, description: String, @RawRes errorRes: Int) {
        textErrorTitle?.setText(titleRes)
        textErrorDescription?.text = description
        animationViewError?.setAnimation(errorRes)
    }

    private fun stateIsDisabled(state: State): Boolean {
        return disabledStates.contains(state)
    }

    private class LayoutResProvider(context: Context, attrs: AttributeSet?) {

        companion object {
            @LayoutRes
            val DEFAULT_ERROR_LAYOUT = R.layout.view_state_error

            @LayoutRes
            val DEFAULT_LOADING_LAYOUT = R.layout.view_state_loading
        }

        @LayoutRes val loadingRes: Int
        @LayoutRes val errorRes: Int

        init {
            if (attrs != null) {
                val array = context.obtainStyledAttributes(attrs, R.styleable.StateViewFlipper)

                val loadingId = array.getResourceId(R.styleable.StateViewFlipper_loadingLayoutRes, -1)
                loadingRes = if (loadingId == -1) DEFAULT_LOADING_LAYOUT else loadingId

                errorRes = array.getResourceId(
                    R.styleable.StateViewFlipper_errorLayoutRes,
                    DEFAULT_ERROR_LAYOUT
                )

                array.recycle()
            } else {
                loadingRes = DEFAULT_LOADING_LAYOUT
                errorRes = DEFAULT_ERROR_LAYOUT
            }
        }
    }
}