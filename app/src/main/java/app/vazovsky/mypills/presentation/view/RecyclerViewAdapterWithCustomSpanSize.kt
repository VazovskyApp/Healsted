package app.vazovsky.mypills.presentation.view

/**
 * Определение spanSize на лету для элементов списка
 * Используется в
 * @see GridSpaceItemDecoration
 */
interface RecyclerViewAdapterWithCustomSpanSize {
    fun getSpanSize(position: Int): Int
}