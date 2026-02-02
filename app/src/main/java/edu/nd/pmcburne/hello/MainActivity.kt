package edu.nd.pmcburne.hello

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.nd.pmcburne.hello.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    /**
     * Retrieve our CounterDao from our database
     */
    private val counterDao by lazy {
        val database = CounterDatabase.getDatabase(applicationContext)
        return@lazy database.counterDao()
    }

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    /**
     * Because we need to pass information into our view model, we need to update how that
     * view model is created via a factoryProducer.
     *
     * Specifically here, we are injecting our CounterDao into our view model.
     */
    @Suppress("UNCHECKED_CAST")
    private val mainViewModel by viewModels<MainViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(counterDao, dataStore) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(viewModel = mainViewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

