package app.vazovsky.healsted.presentation.pilleditor

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import app.vazovsky.healsted.R
import app.vazovsky.healsted.data.model.DatesTakenType
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.TakePillType
import app.vazovsky.healsted.databinding.FragmentPillEditorBinding
import app.vazovsky.healsted.extensions.checkInputs
import app.vazovsky.healsted.extensions.fitKeyboardInsetsWithPadding
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.extensions.showErrorSnackbar
import app.vazovsky.healsted.extensions.toOffsetDateTime
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.base.BaseFragment
import app.vazovsky.healsted.presentation.pills.REQUEST_KEY_UPDATE_PILLS
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber
import androidx.constraintlayout.widget.R as ConstraintR

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
            result.doOnSuccess { setUpdatePillTask(it) }
            result.doOnFailure { Timber.d(it.message) }
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

    override fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) = with(binding) {
        linearLayoutParametres.updatePadding(bottom = bottomNavigationViewHeight)
    }

    private fun setConfirmClick() = with(binding) {
        buttonConfirm.setOnClickListener {
            setFragmentResult(REQUEST_KEY_UPDATE_PILLS, bundleOf())

            val listOfInputs = mutableListOf(textInputName, textInputDosage, textInputTime, textInputStartDate)
            if (switchEndDateEnabled.isChecked) listOfInputs.add(textInputEndDate)

            val validated = listOfInputs.checkInputs()
            if (validated) {
                try {
                    val newPill = pill?.let {
                        pill?.copy(
                            name = editTextName.text.toString(),
                            amount = editTextDosage.text.toString().toFloatOrNull().orDefault(),
                            times = listOf(dateFormatter.parseTimeFromString(editTextTime.text.toString())),
                            startDate = dateFormatter.parseDateFromString(editTextStartDate.text.toString()),
                            datesTaken = spinnerDatesTakenType.selectedItem as DatesTakenType,
                            takePillType = spinnerTakePillType.selectedItem as TakePillType,
                            endDate = if (switchEndDateEnabled.isChecked) {
                                dateFormatter.parseDateFromString(editTextEndDate.text.toString())
                            } else null,
                            comment = editTextComment.text.toString(),
                        )
                    } ?: Pill(
                        name = editTextName.text.toString(),
                        amount = editTextDosage.text.toString().toFloatOrNull().orDefault(),
                        times = listOf(dateFormatter.parseTimeFromString(editTextTime.text.toString())),
                        startDate = dateFormatter.parseDateFromString(editTextStartDate.text.toString()),
                        datesTaken = spinnerDatesTakenType.selectedItem as DatesTakenType,
                        takePillType = spinnerTakePillType.selectedItem as TakePillType,
                        endDate = if (switchEndDateEnabled.isChecked) {
                            dateFormatter.parseDateFromString(editTextEndDate.text.toString())
                        } else null,
                        comment = editTextComment.text.toString(),
                    )
                    if (pill == null) viewModel.addPill(newPill) else viewModel.updatePill(newPill)
                } catch (e: Exception) {
                    showErrorSnackbar(e.message.toString())
                }
            }
        }
    }

    /** Настройка тулбара */
    private fun setupToolbar() = with(binding.toolbar) {
        setNavigationOnClickListener { viewModel.navigateBack() }
        title = resources.getString(pill?.let { R.string.pill_editor_title_edit_pill } ?: R.string.pill_editor_title_add_pill)
    }

    /** Заполнение значений из Pill */
    private fun setupDataIfPillEsNotNull() = with(binding) {
        editTextName.setText(pill?.name.orDefault())
        editTextDosage.setText(pill?.amount.orDefault(1F).toString())
        editTextTime.setText(dateFormatter.formatTime(pill?.times?.first().orDefault()))
        editTextStartDate.setText(dateFormatter.formatStandardDateFull(pill?.startDate.orDefault().toOffsetDateTime()))
        editTextEndDate.setText(dateFormatter.formatStandardDateFull(pill?.endDate.orDefault().toOffsetDateTime()))
        editTextComment.setText(pill?.comment.orDefault())
    }

    /** Настройка спиннера для DatesTakenType */
    private fun setDatesTakenType() = with(binding) {
        val listOfDatesTakenType = DatesTakenType.values()
        spinnerDatesTakenType.adapter = ArrayAdapter(
            requireContext(), ConstraintR.layout.support_simple_spinner_dropdown_item, listOfDatesTakenType
        )
        pill?.let { spinnerDatesTakenType.setSelection(listOfDatesTakenType.indexOf(it.datesTaken)) }
    }

    /** Настройка спиннера для TakePillType */
    private fun setTakePillType() = with(binding) {
        val listOfTakePillType = TakePillType.values()
        spinnerTakePillType.adapter = ArrayAdapter(
            requireContext(), ConstraintR.layout.support_simple_spinner_dropdown_item, listOfTakePillType
        )
        pill?.let { spinnerTakePillType.setSelection(listOfTakePillType.indexOf(it.takePillType)) }
    }

    /** Настройка времени */
    private fun setTimeNotification() = with(binding) {
        buttonAddTime.setOnClickListener { /** TODO Придумать добавление и enabled кнопки */ }
    }

    /** Настройка дат */
    private fun setDateNotification() = with(binding) {
        switchEndDateEnabled.setOnCheckedChangeListener { _, isChecked -> cardViewEndDateNotification.isVisible = isChecked }
    }

    private fun setUpdatePillTask(task: Task<Void>) = with(task) {
        addOnSuccessListener { viewModel.navigateBack() }
        addOnFailureListener { Timber.d(it.message) }
    }
}