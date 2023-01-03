package app.vazovsky.healsted.presentation.addpill

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.DatesTakenType
import app.vazovsky.healsted.data.model.TakePillType
import app.vazovsky.healsted.databinding.FragmentAddPillBinding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding

class AddPillFragment : BaseFragment(R.layout.fragment_add_pill) {

    override val showBottomNavigationView: Boolean
        get() = true

    private val binding by viewBinding(FragmentAddPillBinding::bind)
    private val viewModel: AddPillViewModel by viewModels()

    override fun callOperations() {

    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()
        setDatesTakenType()
        setTakePillType()
        setTimeNotification()
        setDateNotification()
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

    private fun setDateNotification() = with(binding) {
        switchEndDateEnabled.setOnCheckedChangeListener { buttonView, isChecked ->
            linearLayoutEndDateNotification.isVisible = isChecked
        }
    }
}