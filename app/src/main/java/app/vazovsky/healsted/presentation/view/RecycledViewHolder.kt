package app.vazovsky.healsted.presentation.view

/** Интерфейс для холдера с методом, который вызывается при отсоединении view */
interface RecycledViewHolder {
    fun onRecycled()
}