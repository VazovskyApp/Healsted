package app.vazovsky.mypills.presentation.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.core.view.doOnPreDraw
import androidx.core.view.forEach
import androidx.core.view.marginBottom
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import app.vazovsky.mypills.R
import app.vazovsky.mypills.extensions.getColorCompat
import app.vazovsky.mypills.extensions.updateMargins
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.MaterialShapeDrawable

class MainBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : BottomNavigationView(context, attrs, defStyleAttr), NavController.OnDestinationChangedListener {
    init {
        val cornerSize = resources.getDimension(R.dimen.bottom_navigation_view_corner_size)
        val bottomNavigationViewBackground = background as MaterialShapeDrawable
        bottomNavigationViewBackground.shapeAppearanceModel =
            bottomNavigationViewBackground.shapeAppearanceModel.toBuilder()
                .setAllCornerSizes(cornerSize)
                .setTopEdge(
                    FabEdgeTreatment(
                        fabDiameter = resources.getDimension(R.dimen.bottom_navigation_services_fab_size),
                        fabMargin = resources.getDimension(R.dimen.bottom_navigation_fab_margin),
                        centerCornersRadius = resources.getDimension(R.dimen.bottom_navigation_small_corner_size),
                    )
                ).build()

        doOnPreDraw {
            if (isCompatShadow()) {
                val elevationCompat = resources.getDimension(R.dimen.bottom_navigation_view_elevation_compat)
                bottomNavigationViewBackground.elevation = elevationCompat
            }
        }
        bottomNavigationViewBackground.setShadowColor(context.getColorCompat(R.color.dove_gray))
        itemIconTintList = null
    }

    private fun isCompatShadow(): Boolean {
        return (background as MaterialShapeDrawable).requiresCompatShadow()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setupFabPosition()
    }

    private fun setupFabPosition() {
        val fab = findDashboardFab()
        if (fab != null) {
            val bottomMargin = this.height - resources.getDimensionPixelSize(R.dimen.bottom_navigation_small_corner_size)
                .toFloat() - fab.height / 2 + this.marginBottom
            fab.updateMargins(bottom = bottomMargin.toInt())
        }
    }

    /** Находит FAB сервисов в нижнем меню */
    private fun findDashboardFab(): View? {
        if (parent !is FrameLayout) {
            // If we aren't in a CoordinatorLayout we won't have a dependent FAB.
            return null
        }
        val dependents = (parent as FrameLayout).children
        for (v in dependents) {
            if (v is FloatingActionButton || v is ExtendedFloatingActionButton) {
                return v
            }
        }
        return null
    }

    /** Прикрепить NavController */
    fun attachNavController(navController: NavController) {
        // установка слушателя переходов в графe
        navController.addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        fun setStateDashboardFab(menuItem: MenuItem) {
            findDashboardFab()?.isSelected = (menuItem.itemId == R.id.dashboard_graph)
        }

        val graphId = destination.parent?.id ?: return
        if (menu.findItem(graphId) != null) {
            menu.forEach { menuItem ->
                menuItem.isChecked = true
                setStateDashboardFab(menuItem)
            }
        }
    }
}