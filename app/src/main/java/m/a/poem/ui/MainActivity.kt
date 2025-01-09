package m.a.poem.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import m.a.compilot.common.RouteNavigator
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.poem.ui.book.navigation.bookGraph
import m.a.poem.ui.home.HomeRoute
import m.a.poem.ui.home.homeGraph
import m.a.poem.ui.home.routes.navigator
import m.a.poem.ui.poem.navigation.poemGraph
import m.a.poem.ui.poet.navigation.poetGraph
import m.a.poem.ui.search.navigation.searchGraph
import m.a.poem.ui.shared.ui.LocalWindowSize
import m.a.poem.ui.theme.PoemTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val navigationFlow = MutableStateFlow<RouteNavigator?>(null)

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIntentDestination()
        enableEdgeToEdge()
        setContent {
            PoemTheme {
                val navigation = rememberNavController()
                CompositionLocalProvider(LocalNavController provides navigation) {
                    CompositionLocalProvider(
                        LocalWindowSize provides calculateWindowSizeClass(this)
                    ) {
                        NavHost(
                            navController = navigation,
                            startDestination = HomeRoute.navigator(),
                            enterTransition = { EnterTransition.None },
                            exitTransition = { ExitTransition.None },
                        ) {
                            this.homeGraph()
                            this.poetGraph()
                            this.bookGraph()
                            this.poemGraph()
                            this.searchGraph()

                        }
                    }
                    val navController = LocalNavController.comPilotNavController
                    LaunchedEffect(Unit) {
                        navigationFlow.collect {
                            it?.let {
                                navController.safeNavigate().navigate(it)
                            }
                            navigationFlow.update { null }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkIntentDestination()
    }

    private fun checkIntentDestination() {
        intent.extras?.getString(KEY_DESTINATION)?.let { navigator ->
            navigationFlow.update {
                object : RouteNavigator {
                    override fun navigator(): String {
                        return navigator
                    }

                    override fun route(): String {
                        return intent.extras?.getString(KEY_DESTINATION_ROUTE).orEmpty()
                    }

                }
            }
            intent.extras?.remove(KEY_DESTINATION)
            intent.extras?.remove(KEY_DESTINATION_ROUTE)
        }
    }

    companion object {
        const val KEY_DESTINATION = "KEY_DESTINATION"
        const val KEY_DESTINATION_ROUTE = "KEY_DESTINATION_ROUTE"
    }
}