package app.vazovsky.mypills.extensions

import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import app.vazovsky.mypills.R
import app.vazovsky.mypills.presentation.view.GridSpaceItemDecoration
import app.vazovsky.mypills.presentation.view.LinearSpaceItemDecoration
import app.vazovsky.mypills.presentation.view.VerticalDividerItemDecoration

fun RecyclerView.addVerticalDividerItemDecoration(
    @DrawableRes drawableRes: Int = R.drawable.view_recycler_view_divider,
    firstDividerPosition: Int = 0,
    shouldDrawFirstDivider: Boolean = false,
    endOffset: Int = 1
) {
    context.getDrawableCompat(drawableRes)?.let {
        addItemDecoration(
            VerticalDividerItemDecoration(
                it,
                firstDividerPosition,
                shouldDrawFirstDivider,
                endOffset
            )
        )
    }
}

fun RecyclerView.addLinearSpaceItemDecoration(
    @DimenRes spacing: Int = R.dimen.padding_4,
    showFirstVerticalDivider: Boolean = false,
    showLastVerticalDivider: Boolean = false,
    showFirstHorizontalDivider: Boolean = false,
    showLastHorizontalDivider: Boolean = false,
    conditionProvider: LinearSpaceItemDecoration.ConditionProvider? = null,
) {
    this.addItemDecoration(
        LinearSpaceItemDecoration(
            context.resources.getDimensionPixelSize(spacing),
            showFirstVerticalDivider,
            showLastVerticalDivider,
            showFirstHorizontalDivider,
            showLastHorizontalDivider,
            conditionProvider,
        )
    )
}

fun RecyclerView.addDefaultGridSpaceItemDecoration(
    spanCount: Int,
    @DimenRes spacing: Int = R.dimen.padding_8,
    includeEdge: Boolean = false,
    excludeTopEdge: Boolean = true
) {
    val itemDecoration = GridSpaceItemDecoration(
        spanCount,
        context.resources.getDimensionPixelSize(spacing),
        includeEdge,
        excludeTopEdge
    )
    this.addItemDecoration(itemDecoration)
}

fun RecyclerView.disableChangeAnimations() {
    (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
}