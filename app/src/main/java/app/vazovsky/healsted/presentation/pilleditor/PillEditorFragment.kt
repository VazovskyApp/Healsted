package app.vazovsky.healsted.presentation.pilleditor

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.DatesTakenType
import app.vazovsky.healsted.data.model.TakePillType
import app.vazovsky.healsted.databinding.FragmentPillEditorBinding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding

class PillEditorFragment : BaseFragment(R.layout.fragment_pill_editor) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentPillEditorBinding::bind)
    private val viewModel: PillEditorViewModel by viewModels()

    private val args: PillEditorFragmentArgs by navArgs()
    private val pill by lazy { args.pill }

    override fun callOperations() = Unit

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitTopInsetsWithPadding()

        setupToolbar()

        setDatesTakenType()
        setTakePillType()
        setTimeNotification()
        setDateNotification()
    }

    private fun setupToolbar() = with(binding.toolbar) {
        setNavigationOnClickListener { viewModel.navigateBack() }
        title = resources.getString(pill?.let { R.string.pill_editor_title_edit_pill } ?: R.string.pill_editor_title_add_pill)
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
            /** TODO Придумать добавление и enabled кнопки */
        }
    }

    private fun setDateNotification() = with(binding) {
        switchEndDateEnabled.setOnCheckedChangeListener { _, isChecked ->
            linearLayoutEndDateNotification.isVisible = isChecked
        }
    }
}