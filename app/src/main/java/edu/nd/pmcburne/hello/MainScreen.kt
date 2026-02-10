package edu.nd.pmcburne.hello

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onEditNavigation: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            "Welcome to the Counter App!"
        )
        Spacer(modifier = modifier.height(8.dp))
        DataStoreTextFieldExample(viewModel)
        Spacer(modifier = modifier.height(8.dp))
        NewCounterButton(viewModel)
        MainCounterColumn(viewModel, onEditNavigation)
    }
}

@Composable
fun DataStoreTextFieldExample(viewModel: MainViewModel) {
    val savedName by viewModel.savedTextField.collectAsState(initial = "")
    var textFieldValue by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(savedName) {
        textFieldValue = savedName
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Saved Name: $savedName",
            style = MaterialTheme.typography.bodyLarge
        )

        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            label = { Text("Savable text field") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.saveTextField(textFieldValue) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save")
        }
    }
}

@Composable
fun NewCounterButton(viewModel: MainViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = { viewModel.addNewCounter() }
        ){
            Icon(
                Icons.TwoTone.Add,
                contentDescription = "add counter",
                tint = Color.White,
                modifier = Modifier
                    .size(120.dp)
                    .background(MaterialTheme.colorScheme.secondary)
                    .clip(CircleShape)
            )
        }
    }
}


/**
 * This preview is now broken since we can't initialize our view model without the app context
 * or underlying database. We'll address how to resolve this in the very near future!
 */
//@Composable
//@Preview(showBackground = true)
//fun PreviewMainScreen() {
//    MyApplicationTheme {
//        MainScreen(viewModel = hmm.... how do we inject the database without app context?))
//    }
//}

@Composable
fun MainCounterColumn(
    viewModel: MainViewModel,
    onEditNavigation: (Long) -> Unit
) {
    val counters = viewModel.countersState.collectAsState().value
    CounterColumn(
        counters = counters,
        isDecrementEnabled = { counter -> viewModel.isDecrementEnabled(counter) },
        isResetEnabled = { counter -> viewModel.isResetEnabled(counter) },
        onEditNavigation = onEditNavigation,
        onCounterCardEvent = { event, counter -> viewModel.onCounterCardEvent(event, counter) },
    )
}

@Composable
fun CounterColumn(
    counters: List<Counter>,
    isDecrementEnabled: (Counter) -> Boolean,
    isResetEnabled: (Counter) -> Boolean,
    onEditNavigation: (Long) -> Unit,
    onCounterCardEvent: (CounterEvent, Counter) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(counters) { counter ->
                CounterCard(
                    counter = counter,
                    isDecrementEnabled = isDecrementEnabled(counter),
                    isResetEnabled = isResetEnabled(counter),
                    isEditVisible = true,
                    onEditClick = { onEditNavigation(counter.uid) },
                    onCounterEvent = { event -> onCounterCardEvent(event, counter)}
                )
            }
        }
    }
}

@Preview(name = "Counter Column preview", showBackground = true)
@Preview(name = "Counter Column preview - DarkMode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun counterColumnPreview() {
    CounterColumn(
        counters = listOf(
            Counter("Sit-ups", 10, 1),
            Counter("Push-ups", 20, 2),
        ),
        isDecrementEnabled = { _ -> true },
        isResetEnabled = { _ -> false },
        onEditNavigation = { },
        onCounterCardEvent = { _ , _ -> }
    )
}
