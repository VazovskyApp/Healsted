package app.vazovsky.mypills.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.updatePaddingRelative
import app.vazovsky.mypills.R
import app.vazovsky.mypills.extensions.errorCleaner
import com.google.android.material.textfield.TextInputLayout

/**
 * Класс с возможностью валидировать введенные данные
 * @see validate
 *
 * Поле может быть пустым, если атрибут errorEmpty не выставлен
 * Поле проверяется на правильность, если атрибут validationPattern (регулярное выражение) выставлен
 */
class ValidationTextInputLayout : TextInputLayout, TextInputValidator {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

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

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ValidationTextInputLayout,
                0,
                0
            )
            try {
                emptyError = a.getString(R.styleable.ValidationTextInputLayout_errorEmpty) ?: ""
                malformedError = a.getString(R.styleable.ValidationTextInputLayout_errorMalformed) ?: ""
                validationPattern = a.getString(R.styleable.ValidationTextInputLayout_validationPattern) ?: ""
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

    fun setEmptyError(error: String) {
        emptyError = error
    }

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

    override fun getText(): String {
        return editText?.text?.trim()?.toString().orEmpty()
    }

    override fun getLayoutId(): Int {
        return id
    }
}
