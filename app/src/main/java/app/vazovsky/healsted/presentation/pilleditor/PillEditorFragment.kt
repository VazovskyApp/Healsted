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
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.TakePillType
import app.vazovsky.healsted.databinding.FragmentPillEditorBinding
import app.vazovsky.healsted.extensions.fitKeyboardInsetsWithPadding
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.extensions.showErrorSnackbar
import app.vazovsky.healsted.extensions.toOffsetDateTime
import app.vazovsky.healsted.extensions.toStartOfDayTimestamp
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber

/** Экран редактирования или добавления лекарства */
@AndroidEntryPoint
class PillEditorFragment : BaseFragment(R.layout.fragment_pill_editor) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentPillEditorBinding::bind)
    private val viewModel: PillEditorViewModel by viewModels()

    private val args: PillEditorFragmentArgs by navArgs()
    private val pill by lazy { args.pill }

    @Inject lateinit var dateFormatter: DateFormatter

    override fun callOperations() = Unit

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        updatePillLiveData.observe { result ->
            result.doOnSuccess { task ->
                task.addOnSuccessListener {
                    viewModel.navigateBack()
                }
                task.addOnFailureListener { exception ->
                    showErrorSnackbar(exception.message.toString())
                }
            }
            result.doOnFailure {
                showErrorSnackbar(it.message)
            }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitKeyboardInsetsWithPadding()

        setupToolbar()
        setupDataIfPillEsNotNull()

        setDatesTakenType()
        setTakePillType()
        setTimeNotification()
        setDateNotification()

        setConfirmClick()

    }

    private fun setConfirmClick() = with(binding) {
        buttonConfirm.setOnClickListener {
            val newPill = pill?.let {
                pill?.copy(
                    name = editTextName.text.toString(),
                    amount = editTextDosage.text.toString().toFloatOrNull().orDefault(),
                    times = listOf(dateFormatter.parseTimeFromString(editTextTime.text.toString())),
                    startDate = dateFormatter.parseDateFromString(editTextStartDate.text.toString()).toStartOfDayTimestamp(),
                    endDate = if (switchEndDateEnabled.isChecked) {
                        dateFormatter.parseDateFromString(editTextEndDate.text.toString()).toStartOfDayTimestamp()
                    } else null,
                    comment = editTextComment.text.toString(),
                )
            } ?: Pill(
                name = editTextName.text.toString(),
                amount = editTextDosage.text.toString().toFloatOrNull().orDefault(),
                times = listOf(dateFormatter.parseTimeFromString(editTextTime.text.toString())),
                startDate = dateFormatter.parseDateFromString(editTextStartDate.text.toString()).toStartOfDayTimestamp(),
                endDate = if (switchEndDateEnabled.isChecked) {
                    dateFormatter.parseDateFromString(editTextEndDate.text.toString()).toStartOfDayTimestamp()
                } else null,
                comment = editTextComment.text.toString(),
            )
            Timber.d(newPill.toString())
            if (pill == null) viewModel.addPill(newPill) else viewModel.updatePill(newPill)
        }
    }

    override fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) = with(binding) {
        linearLayoutParametres.updatePadding(bottom = bottomNavigationViewHeight)
    }

    /** Настройка тулбара */
    private fun setupToolbar() = with(binding.toolbar) {
        setNavigationOnClickListener { viewModel.navigateBack() }
        title = resources.getString(pill?.let { R.string.pill_editor_title_edit_pill } ?: R.string.pill_editor_title_add_pill)
    }

    /** Заполнение значений из Pill */
    private fun setupDataIfPillEsNotNull() = with(binding) {
        editTextName.setText(pill?.name.orDefault())
        editTextDosage.setText(pill?.amount.orDefault().toString())
        editTextTime.setText(dateFormatter.formatTime(pill?.times?.first().orDefault()))
        editTextStartDate.setText(dateFormatter.formatStandardDateFull(pill?.startDate.orDefault().toOffsetDateTime()))
        editTextEndDate.setText(dateFormatter.formatStandardDateFull(pill?.endDate.orDefault().toOffsetDateTime()))
        editTextComment.setText(pill?.comment.orDefault())
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