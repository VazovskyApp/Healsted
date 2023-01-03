package app.vazovsky.healsted.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

open class EmptyRecyclerView : RecyclerView {

    var emptyView: View? = null
        set(view) {
            field = view
            checkIfEmpty()
        }

    /**
     * Слушатель изменения видимости [EmptyRecyclerView.emptyView].
     * Вызывается, когда видимость [EmptyRecyclerView.emptyView] меняется.
     *
     * @param emptyViewVisibility - значение видимости [EmptyRecyclerView.emptyView]. Может быть либо [View.VISIBLE]
     * либо [View.GONE]
     */
    var onEmptyViewStateChangeListener: (emptyViewVisibility: Int) -> Unit = {}

    private val observer = object : AdapterDataObserver() {
        override fun onChanged() {
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun checkIfEmpty() {
        val emptyView = this.emptyView
        val adapter = this.adapter
        if (emptyView != null && adapter != null) {
            val emptyViewVisible = adapter.itemCount == 0
            if (emptyViewVisible) {
                isVisible = false
                emptyView.isVisible = true
            } else {
                isVisible = true
                emptyView.isVisible = false
            }
            onEmptyViewStateChangeListener(emptyView.visibility)
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(observer)

        checkIfEmpty()
    }
}