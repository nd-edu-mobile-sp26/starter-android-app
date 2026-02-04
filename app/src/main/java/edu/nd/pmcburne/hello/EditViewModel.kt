package edu.nd.pmcburne.hello

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditViewModel(
    val counterDao: CounterDao,
    val counterId: Long
): ViewModel() {
   val counterState: StateFlow<Counter?> = counterDao.getById(counterId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun changeCounterName(newName: String) {
        viewModelScope.launch {
            counterState.value?.let { counter ->
                counterDao.updateCounter(counter.copy(name = newName))
            }
        }
    }

    fun incrementCounter() {
        viewModelScope.launch {
            counterState.value?.let { counter ->
                counterDao.updateCounter(counter.copy(value = counter.value + 1))
            }
        }
    }

    fun decrementCounter() {
        viewModelScope.launch {
            counterState.value?.let { counter ->
                counterDao.updateCounter(counter.copy(value = counter.value - 1))
            }
        }
    }

    fun resetCounter() {
        viewModelScope.launch {
            counterState.value?.let { counter ->
                counterDao.updateCounter(counter.copy(value = 0))
            }
        }
    }

    fun setCounterValue(newValue: Int) {
        viewModelScope.launch {
            counterState.value?.let { counter ->
                counterDao.updateCounter(counter.copy(value = newValue))
            }
        }
    }

    fun deleteCounter() {
        viewModelScope.launch {
            counterState.value?.let { counter ->
                counterDao.deleteCounter(counter)
            }
        }
    }

    val isDecrementEnabled: Boolean
        get() = (counterState.value?.value ?: 0) > 0

    val isResetEnabled: Boolean
        get() = (counterState.value?.value ?: 0) > 0


}
