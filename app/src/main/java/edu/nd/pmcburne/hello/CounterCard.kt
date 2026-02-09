package edu.nd.pmcburne.hello

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.nd.pmcburne.hello.ui.theme.MyApplicationTheme


sealed interface CounterEvent {
    data object Increment: CounterEvent
    data object Decrement: CounterEvent
    data object Reset: CounterEvent
    data object Delete: CounterEvent
}

@Composable
fun CounterCard(
    counter: Counter,
    isDecrementEnabled: Boolean,
    isResetEnabled: Boolean,
    isEditVisible: Boolean = false,
    onCounterEvent: (CounterEvent) -> Unit,
    onEditClick: () -> Unit = {},
) {
    Surface(modifier = Modifier.padding(4.dp)) {
        Column {
            Row {
                Text(
                    text = "Value: ${counter.name} - ${counter.value}",
                    style = MaterialTheme.typography.titleMedium
                )
                if (isEditVisible) {
                    IconButton(onClick = { onEditClick() }) {
                        Icon(Icons.TwoTone.Edit, contentDescription = "edit counter")
                    }
                }

            }
            Row {
                Button( // increment button
                    onClick = { onCounterEvent(CounterEvent.Increment) },
                ) { Text("+") }
                Button( //decrement button
                    onClick = { onCounterEvent(CounterEvent.Decrement) },
                    enabled = isDecrementEnabled,
                ) { Text("-") }
                Button( // reset button
                    onClick = { onCounterEvent(CounterEvent.Reset) },
                    enabled = isResetEnabled,
                ) {
                    Text("Reset")
                }
                Button( // delete button
                    onClick = { onCounterEvent(CounterEvent.Delete) },
                ) {
                    Icon(Icons.TwoTone.Delete, contentDescription = "delete counter")
                }

            }
        }
    }
}



@Preview(name = "Light Mode Counter - Edit Visible - Buttons Enabled", showBackground = true)
@Preview(name = "Dark Mode Counter - Edit Visible - Buttons Enabled", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CounterCardPreview_EditVisible_ButtonsEnabled() {
    MyApplicationTheme {
        CounterCard(
            counter = Counter(name = "Preview Counter", value = 1),
            isDecrementEnabled = true,
            isResetEnabled = true,
            isEditVisible = true,
            onCounterEvent = {}
        )
    }
}

@Preview(name = "Light Mode Counter - Edit Hidden - Buttons Disabled", showBackground = true)
@Preview(name = "Dark Mode Counter - Edit Hidden - Buttons Enabled", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CounterCardPreview_EditHidden_ButtonsDisabled() {
    MyApplicationTheme {
        CounterCard(
            counter = Counter(name = "Preview Counter", value = 0),
            isDecrementEnabled = false,
            isResetEnabled = false,
            isEditVisible = false,
            onCounterEvent = {}
        )
    }
}