package edu.nd.pmcburne.hello

import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


/** Note that this screen is **not** used in the Counters app, and this is
only an illustrative example */


/** Example view model */
class ToggleViewModel(
    val isOn: Boolean = true
): ViewModel() {
    private val _isOnState = MutableStateFlow(isOn)
    val isOnState: StateFlow<Boolean> = _isOnState.asStateFlow()

    fun toggle() {
        viewModelScope.launch {
            _isOnState.update { oldValue -> !oldValue }
        }
    }
}

/** BEFORE STATE HOISTING */
@Composable
fun StatefulToggleWidget(
    viewModel: ToggleViewModel
) {
    val toggleState = viewModel.isOnState.collectAsState()
    Switch(
        checked = toggleState.value,
        onCheckedChange = { viewModel.toggle() }
    )
}


/** AFTER STATE HOISTING */

@Composable
fun StatefulToggleScreen(
    viewModel: ToggleViewModel
) {
    val isOn = viewModel.isOnState.collectAsState()
    StatelessToggle(
        toggleState = isOn.value,
        onToggle = { viewModel.toggle() }
    )
}

@Composable
fun StatelessToggle(
    toggleState: Boolean = true,
    onToggle: () -> Unit
) {
    Switch(
        checked = toggleState,
        onCheckedChange = { onToggle() }
    )
}

@Preview("Toggle on")
@Composable
fun ToggleScreenPreviewOn() {
    StatelessToggle(toggleState = true, onToggle = { } )
}

@Preview("Toggle off")
@Composable
fun ToggleScreenPreviewOff() {
    StatelessToggle(toggleState = false, onToggle = { } )
}


