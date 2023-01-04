package app.vazovsky.healsted.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.updatePaddingRelative
import app.vazovsky.healsted.R
import app.vazovsky.healsted.extensions.errorCleaner
import app.vazovsky.healsted.extensions.orDefault
import com.google.android.material.textfield.TextInputLayout

/**
 * Класс с возможностью валидировать введенные данные
 * @see validate
 *
 * Поле может быть пустым, если атрибут errorEmpty не выставлен
 * Поле проверяется на правильность, если атрибут validationPattern (регулярное выражение) выставлен
 */
class ValidationTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : TextInputLayout(context, attrs, defStyleAttr), TextInputValidator {

    private var validationPattern: String = ""
    private var isNotMalformed: (String) -> Boolean = {
        if (validationPattern.isEmpty()) {
            true
        } else {
            getText().contains(validationPattern.toRegex())
        }
    }
    private var malformedError: String = ""
    private var isNotEmpty: (String) -> Boolean = {
        if (emptyError.isEmpty()) {
            true
        } else {
            getText().isNotBlank()
        }
    }
    private var emptyError: String = ""

    /** Надо ли проверять поле на пустоту */
    private var emptyCheck: Boolean = true

    init {
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ValidationTextInputLayout,
                0,
                0
            )
            try {
                emptyError = a.getString(R.styleable.ValidationTextInputLayout_errorEmpty).orDefault()
                malformedError = a.getString(R.styleable.ValidationTextInputLayout_errorMalformed).orDefault()
                validationPattern = a.getString(R.styleable.ValidationTextInputLayout_validationPattern).orDefault()
                emptyCheck = a.getBoolean(R.styleable.ValidationTextInputLayout_emptyCheck, true)
            } finally {
                a.recycle()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        errorCleaner()
    }

    override fun validate(): Boolean {
        if (emptyCheck && !isNotEmpty(getText())) {
            error = emptyError
            return false
        }
        if (!isNotMalformed(getText())) {
            error = malformedError
            return false
        }
        return true
    }

    override fun getText() = editText?.text?.trim()?.toString().orEmpty()

    override fun getLayoutId() = id

    /** Установка текста ошибки */
    fun setEmptyError(error: String) {
        emptyError = error
    }

    /** Установка данных для валидации */
    fun setValidationData(pattern: String, error: String) {
        validationPattern = pattern
        malformedError = error
    }

    fun noErrorPadding() {
        val errorParent = getChildAt(1) as ViewGroup?

        errorParent?.updatePaddingRelative(
            end = 0,
        )
    }
}
