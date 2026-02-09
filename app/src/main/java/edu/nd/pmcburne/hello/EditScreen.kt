package edu.nd.pmcburne.hello

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun EditScreen(
    viewModel: EditViewModel,
    onBack: () -> Unit
) {
    val counter by viewModel.counterState.collectAsState()
    var nameEntryText by rememberSaveable { mutableStateOf("") }
    var savedRecently by rememberSaveable { mutableStateOf(false) }
    counter?.let { counter ->
        Column {
            CounterCard(
                counter = counter,
                isEditVisible = false,
                isDecrementEnabled = viewModel.isDecrementEnabled,
                isResetEnabled = viewModel.isResetEnabled,
                onCounterEvent = { event -> viewModel.onCounterCardEvent(event) }
            )
            Text("You can edit the name below")
            OutlinedTextField(
                value = nameEntryText,
                onValueChange = { newEntry ->
                    nameEntryText = newEntry
                    savedRecently = false
                },
                label = { Text("Counter Name") }
            )
            Row {
                Row {
                    TextButton(
                        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                        onClick = {
                            viewModel.changeCounterName(nameEntryText)
                            savedRecently = true
                        },

                        ) {
                        Text("Save")
                    }
                    if (savedRecently) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("just saved!")
                    }
                }
            }
            TextButton(
                modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                onClick = { onBack() } // go back to previous screen
            ) {
                Text("Back")
            }
        }
    }
}