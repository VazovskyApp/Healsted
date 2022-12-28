package app.vazovsky.mypills.presentation.pills

import app.vazovsky.mypills.presentation.base.NavigationCommand
import javax.inject.Inject

class PillsDestinations @Inject constructor() {
    fun addPill() = NavigationCommand.To(
        PillsFragmentDirections.actionPillsFragmentToAddPill()
    )
}