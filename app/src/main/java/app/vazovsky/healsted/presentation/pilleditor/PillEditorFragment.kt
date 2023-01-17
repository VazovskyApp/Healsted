package app.vazovsky.healsted.presentation.pilleditor

import android.icu.util.Calendar
import android.icu.util.TimeZone
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
import app.vazovsky.healsted.extensions.serializeToMap
import app.vazovsky.healsted.extensions.showErrorSnackbar
import app.vazovsky.healsted.extensions.toDefaultString
import app.vazovsky.healsted.extensions.withZeroSecondsAndNano
import app.vazovsky.healsted.managers.DateFormatter
import app.vazovsky.healsted.presentation.base.BaseFragment
import app.vazovsky.healsted.presentation.pilleditor.datestakenselected.DatesTakenSelectedAdapter
import app.vazovsky.healsted.presentation.pilleditor.pilltypes.PillTypesAdapter
import app.vazovsky.healsted.presentation.pilleditor.times.TimeItem
import app.vazovsky.healsted.presentation.pilleditor.times.TimesAdapter
import app.vazovsky.healsted.presentation.pills.REQUEST_KEY_UPDATE_PILLS
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.tasks.Task
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
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
        setupDosageString()
        setupPillTypeRecyclerView()
        setupDaysOfWeekRecyclerView()
        setupTimesRecyclerView()
        setDatesTakenType()
        setTakePillType()

        setConfirmClick()
        setDeleteClick()
    }

    override fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) = with(binding) {
        linearLayoutParametres.updatePadding(bottom = bottomNavigationViewHeight)
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
            onEditClick = { item, position ->
                val timePicker = createTimePicker(item)
                timePicker.show(requireActivity().supportFragmentManager, resources.getString(R.string.calendar_pill_time_tag))
                timePicker.addOnPositiveButtonClickListener {
                    val newTime = item.copy(time = LocalTime.of(timePicker.hour, timePicker.minute))
                    recyclerViewTimes.post {
                        timesAdapter.updateItem(newTime, position)
                    }
                }
            }
            onAddClick = {
                val lastElement = timesAdapter.getLastItem()
                val newItem = TimeItem(UUID.randomUUID().toString(), LocalTime.of(lastElement.time.hour, 0))
                timesAdapter.addItem(newItem)
            }
            onDeleteClick = { item ->
                timesAdapter.deleteItem(item)
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
            val isEndDateEnabled = switchEndDateEnabled.isChecked
            Timber.d("LOL ENABLED:$isEndDateEnabled")

            if (listOf(textInputName, textInputDosage).checkInputs()) {
                try {
                    val newPill = pill?.let {
                        pill?.copy(
                            name = editTextName.text.toString(),
                            amount = editTextDosage.text.toString().toFloatOrNull().orDefault(),
                            type = pillTypesAdapter.getSelectedItemType(),
                            times = timesAdapter.currentList.sortedBy { it.time }.distinctBy { it.time }
                                .associate { it.id to it.time },
                            datesTaken = spinnerDatesTakenType.selectedItem as DatesTakenType,
                            datesTakenSelected = if (isSelectedDates) {
                                datesTakenSelectedAdapter.getSelectedItemsValue()
                            } else arrayListOf(),
                            takePillType = spinnerTakePillType.selectedItem as TakePillType,
                            startDate = dateFormatter.parseLocalDateFromString(textViewStartDateValue.text.toString()),
                            endDate = if (isEndDateEnabled) {
                                dateFormatter.parseLocalDateFromString(textViewEndDateValue.text.toString())
                            } else null,
                            comment = editTextComment.text.toString(),
                        )
                    } ?: Pill(
                        name = editTextName.text.toString(),
                        amount = editTextDosage.text.toString().toFloatOrNull().orDefault(),
                        type = pillTypesAdapter.getSelectedItemType(),
                        times = timesAdapter.currentList.sortedBy { it.time }.distinctBy { it.time }
                            .associate { it.id to it.time },
                        datesTaken = spinnerDatesTakenType.selectedItem as DatesTakenType,
                        datesTakenSelected = if (isSelectedDates) {
                            datesTakenSelectedAdapter.getSelectedItemsValue()
                        } else arrayListOf(),
                        takePillType = spinnerTakePillType.selectedItem as TakePillType,
                        startDate = dateFormatter.parseLocalDateFromString(textViewStartDateValue.text.toString()),
                        endDate = if (isEndDateEnabled) {
                            dateFormatter.parseLocalDateFromString(textViewEndDateValue.text.toString())
                        } else null,
                        comment = editTextComment.text.toString(),
                    )
                    if (pill == null) viewModel.addPill(newPill) else viewModel.updatePill(newPill)
                } catch (e: Exception) {
                    showErrorSnackbar(e.message.toString(), marginBottom = resources.getDimensionPixelOffset(R.dimen.margin_20))
                }
            } else {
                when {
                    !textInputName.validate() -> scrollToError(y = textInputName.top)
                    !textInputDosage.validate() -> scrollToError(y = cardViewAttributes.bottom)
                    else -> showErrorSnackbar(resources.getString(R.string.error_something_wrong_title))
                }
            }
        }
    }

    private fun scrollToError(x: Int = 0, y: Int) = with(binding) {
        nestedScrollView.post {
            nestedScrollView.smoothScrollTo(x, y)
        }
    }

    /** Заполнение значений из Pill */
    private fun setupDataIfPillEsNotNull() = with(binding) {
        editTextName.setText(pill?.name.orDefault())
        timesAdapter.submitList((pill?.times?.map { TimeItem(it.key, it.value) } ?: listOf(
            TimeItem(
                id = UUID.randomUUID().toString(),
                time = LocalTime.now().withZeroSecondsAndNano(),
            )
        )))
        datesTakenSelectedAdapter.submitList(listOf(1, 2, 3, 4, 5, 6, 7).map { DatesTakenSelectedItem(it) })
        pill?.datesTakenSelected?.let { list ->
            list.forEach {
                datesTakenSelectedAdapter.selectItem(it)
            }
        }
        switchEndDateEnabled.setOnCheckedChangeListener { _, isChecked ->
            cardViewEndDateNotification.isVisible = isChecked
        }
        setupStartDate()
        setupEndDate()
        editTextComment.setText(pill?.comment.orDefault())
    }

    private fun setupStartDate() = with(binding) {
        textViewStartDateValue.text = pill?.startDate?.let {
            dateFormatter.formatStringFromLocalDate(it)
        } ?: LocalDate.now().toDefaultString()
        cardViewStartDate.setOnClickListener {
            val calendar = createDatePicker(dateFormatter.parseLocalDateFromString(textViewStartDateValue.text.toString()))
            calendar.show(requireActivity().supportFragmentManager, resources.getString(R.string.calendar_pill_date_tag))
            calendar.addOnPositiveButtonClickListener { millis ->
                textViewStartDateValue.text = dateFormatter.getLocalDateString(millis)
            }
        }
    }

    private fun setupEndDate() = with(binding) {
        textViewEndDateValue.text = pill?.endDate?.let {
            dateFormatter.formatStringFromLocalDate(it)
        } ?: LocalDate.now().plusDays(1).toDefaultString()
        switchEndDateEnabled.isChecked = pill?.endDate != null
        cardViewEndDate.setOnClickListener {
            val calendar = createDatePicker(dateFormatter.parseLocalDateFromString(textViewEndDateValue.text.toString()))
            calendar.show(requireActivity().supportFragmentManager, resources.getString(R.string.calendar_pill_date_tag))
            calendar.addOnPositiveButtonClickListener { millis ->
                textViewEndDateValue.text = dateFormatter.getLocalDateString(millis)
            }
        }
    }

    /** Настройка поля дозировки */
    private fun setupDosageString() = with(binding) {
        val dosageText = pill?.amount?.let { amount ->
            val compareResult = amount.compareTo(amount.toInt())
            if (compareResult > 0) amount else amount.toInt()
        } ?: DEFAULT_DOSAGE_VALUE
        editTextDosage.setText(dosageText.toString())
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


    private fun setUpdatePillTask(task: Task<Void>, pill: Pill, uid: String) = with(task) {
        addOnSuccessListener {
            try {
                notificationCore.cancelWorker(requireActivity().application, pill.id)
                notificationCore.createWorker(
                    application = requireActivity().application,
                    notificationImage = R.drawable.ic_logo_red,
                    uid = uid,
                    pill = pill,
                )
            } catch (e: Exception) {
                Timber.d(e.message)
            }
            viewModel.updateLocalPill(pill)
        }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun setDeletePillTask(task: Task<Void>, pill: Pill) = with(task) {
        addOnSuccessListener {
            try {
                notificationCore.cancelWorker(requireActivity().application, pill.id)
            } catch (e: Exception) {
                Timber.d(e.message)
            }
            viewModel.deleteLocalPill(pill)
        }
        addOnFailureListener { Timber.d(it.message) }
    }

    private fun createTimePicker(item: TimeItem): MaterialTimePicker {
        return MaterialTimePicker.Builder()
            .setTitleText("Выберите время")
            .setHour(item.time.hour)
            .setMinute(item.time.minute)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setInputMode(INPUT_MODE_CLOCK)
            .build()
    }

    private fun createDatePicker(date: LocalDate?): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder
            .datePicker()
            .setTitleText("Выберите дату")
            .apply {
                val today = MaterialDatePicker.todayInUtcMilliseconds()
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.timeInMillis = today
                calendar[Calendar.YEAR] = 1900
                val startDate = calendar.timeInMillis

                calendar.timeInMillis = today
                calendar[Calendar.YEAR] = 3000
                val endDate = calendar.timeInMillis

                calendar.timeInMillis = today
                val todayDate = calendar.timeInMillis

                val openDate = date?.let {
                    calendar.timeInMillis = today
                    calendar[Calendar.YEAR] = date.year
                    calendar[Calendar.MONTH] = date.month.value - 1
                    calendar[Calendar.DAY_OF_MONTH] = date.dayOfMonth
                    calendar.timeInMillis
                } ?: todayDate

                val constraint: CalendarConstraints = CalendarConstraints.Builder()
                    .setOpenAt(openDate)
                    .setStart(startDate)
                    .setEnd(endDate)
                    .build()
                setCalendarConstraints(constraint)
            }
            .build()
    }

    companion object {
        const val DEFAULT_DOSAGE_VALUE = "1"
    }
}
