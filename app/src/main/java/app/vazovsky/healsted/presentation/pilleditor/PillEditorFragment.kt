package app.vazovsky.healsted.presentation.pilleditor

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.DatesTakenType
import app.vazovsky.healsted.data.model.TakePillType
import app.vazovsky.healsted.databinding.FragmentPillEditorBinding
import app.vazovsky.healsted.extensions.fitKeyboardInsetsWithPadding
import app.vazovsky.healsted.extensions.fitTopInsetsWithPadding
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import android.R as AndroidR

/** Экран редактирования или добавления лекарства */
@AndroidEntryPoint
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
        root.fitKeyboardInsetsWithPadding()

        setupToolbar()

        setDatesTakenType()
        setTakePillType()
        setTimeNotification()
        setDateNotification()
    }

    override fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) = with(binding) {
        linearLayoutParametres.updatePadding(bottom = bottomNavigationViewHeight)
    }

    private fun setupToolbar() = with(binding.toolbar) {
        setNavigationOnClickListener { viewModel.navigateBack() }
        title = resources.getString(pill?.let { R.string.pill_editor_title_edit_pill } ?: R.string.pill_editor_title_add_pill)
    }

    private fun setDatesTakenType() = with(binding) {
        spinnerDatesTakenType.adapter = ArrayAdapter(
            requireContext(),
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
            DatesTakenType.values()
        )
    }

    private fun setTakePillType() = with(binding) {
        spinnerTakePillType.adapter = ArrayAdapter(
            requireContext(),
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
            TakePillType.values()
        )
    }

    private fun setTimeNotification() = with(binding) {

        buttonAddTime.setOnClickListener {
            /** TODO Придумать добавление и enabled кнопки */
        }
    }

    private fun setDateNotification() = with(binding) {
        switchEndDateEnabled.setOnCheckedChangeListener { _, isChecked ->
            cardViewEndDateNotification.isVisible = isChecked
        }
    }
}