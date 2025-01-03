package m.a.poem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import m.a.compilot.navigation.LocalNavController
import m.a.poem.ui.home.HomeRoute
import m.a.poem.ui.home.homeGraph
import m.a.poem.ui.home.routes.navigator
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
                        startDestination = HomeRoute.navigator()
                    ) {
                        this.homeGraph()
                    }
                }
            }
        }
    }
}