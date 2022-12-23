package app.vazovsky.mypills.presentation.view

/**
 * Интерфейс для валидации текстовых полей во вьюмоделях.
 */
interface TextInputValidator {
    fun validate(): Boolean
    fun getText(): String
    /** Получение ID TIL'а для скролла к некорректным полям */
    fun getLayoutId(): Int
}
