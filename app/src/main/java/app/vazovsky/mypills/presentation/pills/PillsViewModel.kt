package app.vazovsky.mypills.presentation.pills

import app.vazovsky.mypills.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PillsViewModel @Inject constructor(
    private val pillsDestinations: PillsDestinations,
) : BaseViewModel() {
    fun openAddPill() {
        navigate(pillsDestinations.addPill())
    }
}