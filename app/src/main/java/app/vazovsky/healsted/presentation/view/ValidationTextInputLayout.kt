package app.vazovsky.healsted.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.updatePaddingRelative
import app.vazovsky.healsted.R
import app.vazovsky.healsted.extensions.errorCleaner
import com.google.android.material.textfield.TextInputLayout

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
