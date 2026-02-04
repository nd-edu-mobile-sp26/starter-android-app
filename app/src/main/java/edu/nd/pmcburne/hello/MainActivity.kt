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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = HomeRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<HomeRoute> {
                            @Suppress("UNCHECKED_CAST")
                            val mainViewModel = ViewModelProvider(this@MainActivity, object : ViewModelProvider.Factory {
                                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                    return MainViewModel(counterDao, dataStore) as T
                                }
                            })[MainViewModel::class.java]
                            MainScreen(
                                viewModel = mainViewModel,
                                navController = navController
                            )
                        }
                        composable<EditRoute> { backStackEntry ->
                            // Extract ID from the route
                            val route: EditRoute = backStackEntry.toRoute()

                            // Manual Factory for EditViewModel using the ID from navigation
                            @Suppress("UNCHECKED_CAST")
                            val vm = ViewModelProvider(this@MainActivity, object : ViewModelProvider.Factory {
                                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                    return EditViewModel(counterDao, route.id.toLong()) as T
                                }
                            })[EditViewModel::class.java]

                            EditScreen(viewModel = vm, navController = navController)
                        }
                    }
                }
            }
        }
    }
}

