package edu.nd.pmcburne.hello


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

val TEXT_FIELD_CONTENTS = stringPreferencesKey("TEXT_FIELD")

/** the state of the counter on our main screen */


class MainViewModel(
    val counterDao: CounterDao,
    val dataStore: DataStore<Preferences>
): ViewModel() {
    val savedTextField: StateFlow<String> = dataStore.data
        .map { preferences ->
            preferences[TEXT_FIELD_CONTENTS] ?: ""
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    private val _countersState = MutableStateFlow<List<Counter>>(emptyList())
    val countersState: StateFlow<List<Counter>> = _countersState.asStateFlow()



    init {
        viewModelScope.launch {
            counterDao.getAll().collect { newCounters ->
                _countersState.value = newCounters
            }
        }
    }

    /**
     * Persists the text field using data store
     */
    fun saveTextField(newName: String) {
        viewModelScope.launch {
            dataStore.edit { settings ->
                settings[TEXT_FIELD_CONTENTS] = newName
            }
        }
    }

    /**
     * Adds a new counter to the screen
     */
    fun addNewCounter() {
        viewModelScope.launch {
            val maxId = _countersState.value.maxOfOrNull { it.uid }?: 0
            counterDao.insertCounter(Counter(name = "Counter ${maxId + 1}"))
        }
    }

    /** increments the counter by 1 */
    fun incrementCounter(counter: Counter) {
        viewModelScope.launch {
            counterDao.updateCounter(counter.copy(value = counter.value + 1))
        }
    }

    /** decrements the counter by 1 */
    fun decrementCounter(counter: Counter) {
        viewModelScope.launch {
            counterDao.updateCounter(counter.copy(value = counter.value - 1))
        }
    }

    /** resets the counter to 0 */
    fun resetCounter(counter: Counter) {
        viewModelScope.launch {
            counterDao.updateCounter(counter.copy(value = 0))
        }
    }

    /** delete the counter */
    fun deleteCounter(counter: Counter) {
        viewModelScope.launch {
            counterDao.deleteCounter(counter)
        }
    }

    /** checks if the increment button should be enabled */
    fun isDecrementEnabled(counter: Counter): Boolean = counter.value > 0
    /** checks if the decrement button should be enabled */
    fun isResetEnabled(counter: Counter): Boolean = counter.value > 0
}