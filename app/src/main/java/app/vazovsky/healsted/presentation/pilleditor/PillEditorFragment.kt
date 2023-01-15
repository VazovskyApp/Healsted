package app.vazovsky.healsted.presentation.pilleditor

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import app.vazovsky.healsted.R
import app.vazovsky.healsted.core.core.NotificationCore
import app.vazovsky.healsted.data.model.DatesTakenSelectedItem
import app.vazovsky.healsted.data.model.DatesTakenType
import app.vazovsky.healsted.data.model.Pill
import app.vazovsky.healsted.data.model.PillType
import app.vazovsky.healsted.data.model.PillTypeItem
import app.vazovsky.healsted.data.model.TakePillType
import app.vazovsky.healsted.databinding.FragmentPillEditorBinding
import app.vazovsky.healsted.extensions.checkInputs
import app.vazovsky.healsted.extensions.fitKeyboardInsetsWithPadding
import app.vazovsky.healsted.extensions.orDefault
import app.vazovsky.healsted.extensions.showErrorSnackbar
import app.vazovsky.healsted.extensions.toDefaultString
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.base.BaseFragment
import app.vazovsky.healsted.presentation.pilleditor.datestakenselected.DatesTakenSelectedAdapter
import app.vazovsky.healsted.presentation.pilleditor.pilltypes.PillTypesAdapter
import app.vazovsky.healsted.presentation.pilleditor.times.TimeItem
import app.vazovsky.healsted.presentation.pilleditor.times.TimesAdapter
import app.vazovsky.healsted.presentation.pills.REQUEST_KEY_UPDATE_PILLS
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.inject.Inject
import timber.log.Timber
import androidx.constraintlayout.widget.R as ConstraintR


/** Экран редактирования или добавления лекарства */
@AndroidEntryPoint
class PillEditorFragment : BaseFragment(R.layout.fragment_pill_editor) {

    override val showBottomNavigationView = true

    private val binding by viewBinding(FragmentPillEditorBinding::bind)
    private val viewModel: PillEditorViewModel by viewModels()
    @Inject lateinit var notificationCore: NotificationCore

    private val args: PillEditorFragmentArgs by navArgs()
    private val pill by lazy { args.pill }

    @Inject lateinit var dateFormatter: DateFormatter
    @Inject lateinit var pillTypesAdapter: PillTypesAdapter
    @Inject lateinit var timesAdapter: TimesAdapter
    @Inject lateinit var datesTakenSelectedAdapter: DatesTakenSelectedAdapter

    override fun callOperations() {
        viewModel.getPillTypes()
    }

    override fun onBindViewModel() = with(viewModel) {
        observeNavigationCommands()
        pillTypesLiveData.observe { result ->
            binding.stateViewFlipper.setStateFromResult(result)
            result.doOnSuccess { types -> bindTypes(types) }
            result.doOnFailure { Timber.d(it.message) }
        }
        updatePillLiveEvent.observe { result ->
            result.doOnSuccess { setUpdatePillTask(it.task, it.pill, it.uid) }
            result.doOnFailure { Timber.d(it.message) }
        }
        updateLocalPillLiveEvent.observe { result ->
            result.doOnSuccess { viewModel.navigateBack() }
            result.doOnFailure { Timber.d(it.message) }
        }
        deletePillLiveEvent.observe { result ->
            result.doOnSuccess { setDeletePillTask(it.task, it.pill) }
            result.doOnFailure { Timber.d(it.message) }
        }
        deleteLocalPillLiveEvent.observe { result ->
            result.doOnSuccess { viewModel.navigateBack() }
            result.doOnFailure { Timber.d(it.message) }
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.fitKeyboardInsetsWithPadding()
        stateViewFlipper.setRetryMethod { viewModel.getPillTypes() }

        setupToolbar()
        setupDataIfPillEsNotNull()
        setupPillTypeRecyclerView()
        setupDaysOfWeekRecyclerView()
        setupTimesRecyclerView()
        setDatesTakenType()
        setTakePillType()
        setDateNotification()

        setConfirmClick()
        setDeleteClick()
    }

    private fun setupDosage() = with(binding) {
        val type = pillTypesAdapter.getSelectedItemType()
        textInputDosage.hint = when (type) {
            PillType.LIQUID -> buildString {
                append(type.toString())
                append(", ")
                append(resources.getString(R.string.pill_type_liquid))
            }

            PillType.CREAM -> buildString {
                append(type.toString())
                append(", ")
                append(resources.getString(R.string.pill_type_cream))
            }

            else -> buildString {
                append(type.toString())
                append(", ")
                append(resources.getString(R.string.pill_type_other))
            }
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

    private fun bindTypes(types: List<PillTypeItem>) = with(pillTypesAdapter) {
        submitList(types)
        selectItem(pill?.type ?: PillType.TABLETS)
        setupDosage()
    }

    /** Настройка типа лекарства */
    private fun setupPillTypeRecyclerView() = with(binding) {
        recyclerViewPillType.adapter = pillTypesAdapter.apply {
            onItemClick = { item ->
                this.selectItem(item.pillType)
                recyclerViewPillType.invalidate()
                setupDosage()
            }
        }
    }

    /** Настройка времени уведомления */
    private fun setupTimesRecyclerView() = with(binding) {
        recyclerViewTimes.adapter = timesAdapter.apply {
            onDeleteClick = { item ->
                recyclerViewTimes.post {
                    timesAdapter.deleteItem(item)
                }
            }
            editTime = { newPair, position ->
                recyclerViewTimes.post {
                    timesAdapter.updateItem(newPair, position)
                }
            }
        }
        buttonAddTime.setOnClickListener {
            recyclerViewTimes.post {
                timesAdapter.addItem(TimeItem(UUID.randomUUID().toString(), LocalTime.now()))
            }
        }
    }

    /** Настройка выбранных дней недели для уведомления */
    private fun setupDaysOfWeekRecyclerView() = with(binding) {
        recyclerViewDatesTakenSelected.adapter = datesTakenSelectedAdapter.apply {
            onItemClick = { item ->
                this.selectItem(item.value)
                recyclerViewDatesTakenSelected.invalidate()
            }
        }
    }

    /** Настройка кнопки удаления */
    private fun setDeleteClick() = with(binding.toolbar) {
        menu.findItem(R.id.menuDelete).isVisible = pill != null
        setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.menuDelete) {
                setFragmentResult(REQUEST_KEY_UPDATE_PILLS, bundleOf())
                pill?.let { viewModel.deletePill(it) }
            }
            true
        }
    }

    /** Настройка кнопки подтверждения */
    private fun setConfirmClick() = with(binding) {
        buttonConfirm.setOnClickListener {
            setFragmentResult(REQUEST_KEY_UPDATE_PILLS, bundleOf())

            val datesTakenType = spinnerDatesTakenType.selectedItem as DatesTakenType
            val isSelectedDates = datesTakenType == DatesTakenType.SELECTED_DAYS

            if (datesTakenSelectedAdapter.getSelectedItemsValue().isEmpty() && isSelectedDates) {
                showErrorSnackbar(
                    "Выберите хотя бы один день для приема лекарства, либо измените частоту приема",
                    marginBottom = resources.getDimensionPixelOffset(R.dimen.margin_20),
                )
                return@setOnClickListener
            }

            val listOfInputs = mutableListOf(textInputName, textInputDosage, textInputStartDate)
            if (switchEndDateEnabled.isChecked) listOfInputs.add(textInputEndDate)
            val validated = listOfInputs.checkInputs()
            if (validated) {
                try {
                    val newPill = pill?.let {
                        pill?.copy(
                            name = editTextName.text.toString(),
                            amount = editTextDosage.text.toString().toFloatOrNull().orDefault(),
                            type = pillTypesAdapter.getSelectedItemType(),
                            times = timesAdapter.currentList.distinctBy { it.time }.associate { it.id to it.time },
                            datesTaken = spinnerDatesTakenType.selectedItem as DatesTakenType,
                            datesTakenSelected = if (isSelectedDates) {
                                datesTakenSelectedAdapter.getSelectedItemsValue()
                            } else arrayListOf(),
                            takePillType = spinnerTakePillType.selectedItem as TakePillType,
                            startDate = dateFormatter.parseLocalDateFromString(editTextStartDate.text.toString()),
                            endDate = if (switchEndDateEnabled.isChecked) {
                                dateFormatter.parseLocalDateFromString(editTextEndDate.text.toString())
                            } else null,
                            comment = editTextComment.text.toString(),
                        )
                    } ?: Pill(
                        name = editTextName.text.toString(),
                        amount = editTextDosage.text.toString().toFloatOrNull().orDefault(),
                        type = pillTypesAdapter.getSelectedItemType(),
                        times = timesAdapter.currentList.distinctBy { it.time }.associate { it.id to it.time },
                        datesTaken = spinnerDatesTakenType.selectedItem as DatesTakenType,
                        datesTakenSelected = if (isSelectedDates) {
                            datesTakenSelectedAdapter.getSelectedItemsValue()
                        } else arrayListOf(),
                        takePillType = spinnerTakePillType.selectedItem as TakePillType,
                        startDate = dateFormatter.parseLocalDateFromString(editTextStartDate.text.toString()),
                        endDate = if (switchEndDateEnabled.isChecked) {
                            dateFormatter.parseLocalDateFromString(editTextEndDate.text.toString())
                        } else null,
                        comment = editTextComment.text.toString(),
                    )
                    if (pill == null) viewModel.addPill(newPill) else viewModel.updatePill(newPill)
                } catch (e: Exception) {
                    showErrorSnackbar(e.message.toString(), marginBottom = resources.getDimensionPixelOffset(R.dimen.margin_20))
                }
            }
        }
    }

    /** Заполнение значений из Pill */
    private fun setupDataIfPillEsNotNull() = with(binding) {
        editTextName.setText(pill?.name.orDefault())
        editTextDosage.setText(pill?.amount.orDefault(1F).toString())
        timesAdapter.submitList(pill?.times?.map { TimeItem(it.key, it.value) } ?: listOf(
            TimeItem(
                id = UUID.randomUUID().toString(),
                time = LocalTime.now(),
            )
        ))
        datesTakenSelectedAdapter.submitList(
            listOf(
                DatesTakenSelectedItem(1),
                DatesTakenSelectedItem(2),
                DatesTakenSelectedItem(3),
                DatesTakenSelectedItem(4),
                DatesTakenSelectedItem(5),
                DatesTakenSelectedItem(6),
                DatesTakenSelectedItem(7),
            )
        )
        pill?.datesTakenSelected?.let { list ->
            list.forEach {
                datesTakenSelectedAdapter.selectItem(it)
            }
        }
        editTextStartDate.setText(pill?.startDate?.let {
            dateFormatter.formatStringFromLocalDate(it)
        } ?: LocalDate.now().toDefaultString())
        editTextEndDate.setText(pill?.endDate?.let {
            dateFormatter.formatStringFromLocalDate(it)
        } ?: LocalDate.now().toDefaultString())
        editTextComment.setText(pill?.comment.orDefault())
    }

    /** Настройка спиннера для DatesTakenType */
    private fun setDatesTakenType() = with(binding) {
        val listOfDatesTakenType = DatesTakenType.values()
        spinnerDatesTakenType.adapter = ArrayAdapter(
            requireContext(), ConstraintR.layout.support_simple_spinner_dropdown_item, listOfDatesTakenType
        )
        spinnerDatesTakenType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                frameLayoutDatesTakenSelected.isVisible = listOfDatesTakenType[position] == DatesTakenType.SELECTED_DAYS
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }
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

    /** Настройка дат */
    private fun setDateNotification() = with(binding) {
        switchEndDateEnabled.setOnCheckedChangeListener { _, isChecked -> cardViewEndDateNotification.isVisible = isChecked }
    }

    private fun setUpdatePillTask(task: Task<Void>, pill: Pill, uid: String) = with(task) {
        addOnSuccessListener {
            notificationCore.cancelWorker(requireActivity().application, pill.id)
            notificationCore.createWorker(
                application = requireActivity().application,
                notificationImage = R.drawable.ic_logo_red,
                uid = uid,
                pill = pill,
            )
            viewModel.updateLocalPill(pill)
        }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun setDeletePillTask(task: Task<Void>, pill: Pill) = with(task) {
        addOnSuccessListener {
            notificationCore.cancelWorker(requireActivity().application, pill.id)
            viewModel.deleteLocalPill(pill)
        }
        addOnFailureListener { Timber.d(it.message) }
    }
}
