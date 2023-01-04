package app.vazovsky.healsted.presentation.pills

import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.presentation.base.NavigationCommand
import javax.inject.Inject

class PillsDestinations @Inject constructor() {
    fun addPill() = NavigationCommand.To(
        PillsFragmentDirections.actionPillsFragmentToPillEditorGraph()
    )

    fun editPill(pill: Pill) = NavigationCommand.To(
        PillsFragmentDirections.actionPillsFragmentToPillEditorGraph(pill)
    )
}