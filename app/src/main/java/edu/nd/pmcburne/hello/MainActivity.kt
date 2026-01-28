package edu.nd.pmcburne.hello

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.nd.pmcburne.hello.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            "Welcome to the Counter App!"
        )
        Spacer(modifier = modifier.height(16.dp))
        Counter(initialValue = 0)
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewMainScreen() {
    MyApplicationTheme {
        MainScreen()
    }
}

@Composable
fun Counter(initialValue: Int = 0, modifier: Modifier = Modifier) {
    var value by rememberSaveable { mutableStateOf(initialValue) }
    Row {
        Text("Value: $value")
        Button(
            onClick = { value++ },
            modifier = modifier
        ) { Text("+") }
        Button(
            onClick = { value-- },
            enabled = value > 0,
            modifier = modifier
        ) {
            Text("-")
        }
        Button(
            onClick = { value = 0 },
            enabled = value > 0,
            modifier = modifier
        ) {
            Text("Reset")
        }

    }
}


@Preview(name = "Light Mode Counter", showBackground = true)
@Preview(name = "Dark Mode Counter", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CounterPreview() {
    MyApplicationTheme {
        Counter(0)
    }
}