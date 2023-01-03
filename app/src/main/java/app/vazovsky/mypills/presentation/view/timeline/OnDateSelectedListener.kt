package app.vazovsky.mypills.presentation.view.timeline

interface OnDateSelectedListener {
    fun onDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int)
    fun onDisabledDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int, isDisabled: Boolean)
}