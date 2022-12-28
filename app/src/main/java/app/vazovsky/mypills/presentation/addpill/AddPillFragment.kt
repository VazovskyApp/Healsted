package app.vazovsky.mypills.presentation.addpill

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import app.vazovsky.mypills.R
import app.vazovsky.mypills.data.model.DatesTakenType
import app.vazovsky.mypills.data.model.TakePillType
import app.vazovsky.mypills.databinding.FragmentAddPillBinding
import app.vazovsky.mypills.extensions.fitTopInsetsWithPadding
import app.vazovsky.mypills.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding


class AddPillFragment : BaseFragment(R.layout.fragment_add_pill) {

    override val showBottomNavigationView: Boolean
        get() = true

    private val binding by viewBinding(FragmentAddPillBinding::bind)
    private val viewModel: AddPillViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
        setDatesTakenType()
        setTakePillType()
        setTimeNotification()
    }

    private fun setDatesTakenType() = with(binding) {
        spinnerDatesTakenType.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, DatesTakenType.values())
    }

    private fun setTakePillType() = with(binding) {
        spinnerTakePillType.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, TakePillType.values())
    }

    private fun setTimeNotification() = with(binding) {
        timePickerTimeNotification.setIs24HourView(true)
        buttonAddTime.setOnClickListener {
            /** Придумать добавление и enabled кнопки */
        }
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }
}