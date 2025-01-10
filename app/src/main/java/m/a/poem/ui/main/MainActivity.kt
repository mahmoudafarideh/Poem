package m.a.poem.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import m.a.compilot.common.RouteNavigator
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.poem.ui.LocalSnackBarHostState
import m.a.poem.ui.artwork.navigation.artworkGraph
import m.a.poem.ui.book.navigation.bookGraph
import m.a.poem.ui.home.navigation.homeGraph
import m.a.poem.ui.info.navigation.infoGraph
import m.a.poem.ui.main.component.PoemPlayerContent
import m.a.poem.ui.omen.navigation.omenGraph
import m.a.poem.ui.poem.navigation.poemGraph
import m.a.poem.ui.poet.navigation.poetGraph
import m.a.poem.ui.search.navigation.searchGraph
import m.a.poem.ui.shared.ui.LocalWindowSize
import m.a.poem.ui.splash.navigation.SplashRoute
import m.a.poem.ui.splash.navigation.routes.navigationRoute
import m.a.poem.ui.splash.navigation.routes.navigator
import m.a.poem.ui.splash.navigation.splashGraph
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
                val snackbarHostState = remember { SnackbarHostState() }
                CompositionLocalProvider(LocalWindowSize provides calculateWindowSizeClass(this)) {
                    CompositionLocalProvider(LocalNavController provides navigation) {
                        CompositionLocalProvider(LocalSnackBarHostState provides snackbarHostState) {
                            MainContent(snackbarHostState, navigation)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun MainContent(
        snackbarHostState: SnackbarHostState,
        navigation: NavHostController,
        modifier: Modifier = Modifier
    ) {
        var splashPassed by remember {
            mutableStateOf(false)
        }
        CheckSplashPassed(navigation) {
            splashPassed = true
        }
        Box(modifier) {
            Column(modifier = Modifier) {
                PoemPlayerContent(this@MainActivity)
                NavHost(
                    navController = navigation,
                    startDestination = SplashRoute.navigator(),
                    enterTransition = { EnterTransition.Companion.None },
                    exitTransition = { ExitTransition.Companion.None },
                ) {
                    this.homeGraph()
                    this.poetGraph()
                    this.bookGraph()
                    this.poemGraph()
                    this.searchGraph()
                    this.omenGraph()
                    this.infoGraph()
                    this.artworkGraph()
                    this.splashGraph()
                }
            }
            val navController = LocalNavController.comPilotNavController
            if (splashPassed) {
                LaunchedEffect(Unit) {
                    navigationFlow.filterNotNull().collect {
                        navController.safeNavigate().navigate(it)
                        navigationFlow.update { null }
                    }
                }
            }
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }

    @Composable
    private fun CheckSplashPassed(
        navigation: NavHostController,
        splashPassed: () -> Unit
    ) {
        DisposableEffect(Unit) {
            val listener = object : OnDestinationChangedListener {
                override fun onDestinationChanged(
                    controller: NavController,
                    destination: NavDestination,
                    arguments: Bundle?
                ) {
                    if(destination.route != SplashRoute.navigationRoute()){
                        splashPassed()
                    }
                }
            }
            navigation.addOnDestinationChangedListener(listener)
            onDispose {
                navigation.removeOnDestinationChangedListener(listener)
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
