package app.vazovsky.healsted.data.model

data class DatesTakenSelectedItem(
    /** День */
    val value: Int,

    /** Выбран ли день */
    var selected: Boolean = false,
)