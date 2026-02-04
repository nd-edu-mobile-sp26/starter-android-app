package edu.nd.pmcburne.hello

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
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavController,
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
        CounterColumn(viewModel, navController)
    }
}

@Composable
fun DataStoreTextFieldExample(viewModel: MainViewModel) {
    // 1. Observe the persisted value from DataStore
    val savedName by viewModel.savedTextField.collectAsState(initial = "")

    // 2. Local state for what is currently in the TextField
    var textFieldValue by rememberSaveable { mutableStateOf("") }

    // Update the local text field when the saved value changes (e.g., on app start)
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
fun CounterColumn(
    viewModel: MainViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val counters = viewModel.countersState.collectAsState().value
        Column(modifier = Modifier.fillMaxWidth()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(counters) { counter ->
                    CounterCard(viewModel, navController, counter)
                }
            }
        }
    }
}

@Composable
fun CounterCard(
    viewModel: MainViewModel,
    navController: NavController,
    counter: Counter,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = Modifier.padding(4.dp)) {
        Column {
            Row {
                Text(
                    text = "Value: ${counter.name} - ${counter.value}",
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(
                    onClick = {
                        navController.navigate(EditRoute(counter.uid))
                    }
                ) {
                    Icon(Icons.TwoTone.Edit, contentDescription = "edit counter")
                }

            }
            Row {
                Button( // increment button
                    onClick = { viewModel.incrementCounter(counter) },
                    modifier = modifier
                ) { Text("+") }
                Button( //decrement button
                    onClick = { viewModel.decrementCounter(counter) },
                    enabled = viewModel.isDecrementEnabled(counter),
                    modifier = modifier
                ) {
                    Text("-")
                }
                Button( // reset button
                    onClick = { viewModel.resetCounter(counter) },
                    enabled = viewModel.isResetEnabled(counter),
                    modifier = modifier
                ) {
                    Text("Reset")
                }
                Button( // delete button
                    onClick = { viewModel.deleteCounter(counter) },
                    modifier = modifier
                ) {
                   Icon(Icons.TwoTone.Delete, contentDescription = "delete counter")
                }

            }
        }
    }
}


/**
 * These previews is now broken since we can't initialize our view model without the app context
 * or underlying database. We'll address how to resolve this in the very near future!
 */
//@Preview(name = "Light Mode Counter", showBackground = true)
//@Preview(name = "Dark Mode Counter", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun CounterCardPreview() {
//    MyApplicationTheme {
//        CounterCard(viewModel = hmm.... how do we inject the database without app context?)
//    }
//}