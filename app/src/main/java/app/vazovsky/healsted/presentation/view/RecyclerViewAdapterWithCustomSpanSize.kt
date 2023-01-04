package app.vazovsky.healsted.presentation.view

/**
 * Определение spanSize для элементов списка
 * Используется в
 * @see GridSpaceItemDecoration
 */
interface RecyclerViewAdapterWithCustomSpanSize {
    fun getSpanSize(position: Int): Int
}