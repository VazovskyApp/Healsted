package app.vazovsky.mypills.presentation.pills

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.vazovsky.mypills.R
import app.vazovsky.mypills.presentation.pills.tab.active.ActivePillsTab
import app.vazovsky.mypills.presentation.pills.tab.completed.CompletedPillsTab

private const val TABS_COUNT = 2
private const val ACTIVE_POSITION = 0
private const val COMPLETED_POSITION = 1

class PillsFragmentPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val context: Context = fragment.requireContext()

    override fun getItemCount() = TABS_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            ACTIVE_POSITION -> ActivePillsTab()
            COMPLETED_POSITION -> CompletedPillsTab()
            else -> throw IllegalStateException("Incorrect position")
        }
    }

    fun getTitle(position: Int) = when (position) {
        ACTIVE_POSITION -> context.getString(R.string.pills_active_tab_title)
        COMPLETED_POSITION -> context.getString(R.string.pills_completed_tab_title)
        else -> throw IllegalStateException("Incorrect position")
    }
}