package app.vazovsky.healsted.presentation.view

/**
 * Интерфейс для валидации текстовых полей во viewModels.
 */
interface TextInputValidator {
    fun validate(): Boolean
    fun getText(): String

    /** Получение ID Til's для скролла к некорректным полям */
    fun getLayoutId(): Int
}
