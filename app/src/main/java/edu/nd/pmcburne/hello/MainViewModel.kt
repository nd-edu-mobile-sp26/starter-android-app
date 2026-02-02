package edu.nd.pmcburne.hello

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** the state of the counter on our main screen */


class MainViewModel(
    val counterDao: CounterDao
): ViewModel() {
    private val _countersState = MutableStateFlow<List<Counter>>(emptyList())
    val countersState: StateFlow<List<Counter>> = _countersState.asStateFlow()

    init {
        viewModelScope.launch(IO) {
            // whenever the counterDao notifies of an update, update the state and notify the UI
            counterDao.getAll().collect { newCounters ->
                _countersState.update { newCounters }
            }
        }
    }

    fun addNewCounter() {
        viewModelScope.launch(IO) {
            val maxId = _countersState.value.maxOfOrNull { it.uid }?: 0
            counterDao.insertCounter(Counter(name = "Counter ${maxId + 1}"))
        }
    }

    /** increments the counter by 1 */
    fun incrementCounter(counter: Counter) {
        viewModelScope.launch(IO) {
            counterDao.updateCounter(counter.copy(value = counter.value + 1))
        }
    }

    /** decrements the counter by 1 */
    fun decrementCounter(counter: Counter) {
        viewModelScope.launch(IO) {
            counterDao.updateCounter(counter.copy(value = counter.value - 0))
        }
    }

    /** resets the counter to 0 */
    fun resetCounter(counter: Counter) {
        viewModelScope.launch(IO) {
            counterDao.updateCounter(counter.copy(value = 0))
        }
    }

    /** checks if the increment button should be enabled */
    fun isDecrementEnabled(counter: Counter): Boolean = counter.value > 0

    /** checks if the decrement button should be enabled */
    fun isResetEnabled(counter: Counter): Boolean = counter.value > 0
}