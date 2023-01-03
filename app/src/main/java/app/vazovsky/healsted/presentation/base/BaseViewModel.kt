package app.vazovsky.healsted.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.vazovsky.healsted.data.model.base.LoadableResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _navigationLiveEvent = SingleLiveEvent<NavigationCommand>()
    val navigationLiveEvent: LiveData<NavigationCommand> = _navigationLiveEvent

    protected fun navigate(navigationCommand: NavigationCommand) {
        _navigationLiveEvent.value = navigationCommand
    }

    fun navigateBack() {
        _navigationLiveEvent.value = (NavigationCommand.Back)
    }

    protected fun <T> MutableLiveData<LoadableResult<T>>.launchLoadData(
        block: Flow<LoadableResult<T>>,
    ): Job = viewModelScope.launch {
        block.collect { result ->
            this@launchLoadData.postValue(result)
        }
    }
}