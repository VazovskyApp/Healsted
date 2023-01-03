package app.vazovsky.healsted.managers

import android.view.View
import androidx.core.view.isVisible

interface BottomNavigationViewManager {
    fun setNavigationViewVisibility(isVisible: Boolean)
    fun getNavigationView(): View

    fun getMenuMarginBottom(): Int {
        return if (getNavigationView().isVisible) {
            getNavigationView().height
        } else {
            0
        }
    }
}