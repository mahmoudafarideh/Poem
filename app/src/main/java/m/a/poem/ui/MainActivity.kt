package m.a.poem.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import m.a.compilot.navigation.LocalNavController
import m.a.poem.ui.home.HomeRoute
import m.a.poem.ui.home.homeGraph
import m.a.poem.ui.home.routes.navigator
import m.a.poem.ui.poet.navigation.poetGraph
import m.a.poem.ui.theme.PoemTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PoemTheme {
                val navigation = rememberNavController()
                CompositionLocalProvider(LocalNavController provides navigation) {
                    NavHost(
                        navController = navigation,
                        startDestination = HomeRoute.navigator(),
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                    ) {
                        this.homeGraph()
                        this.poetGraph()
                    }
                }
            }
        }
    }
}