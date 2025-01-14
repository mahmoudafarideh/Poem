package m.a.nobahar.ui.splash.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.nobahar.domain.model.Loaded
import m.a.nobahar.ui.home.navigation.HomeRoute
import m.a.nobahar.ui.home.navigation.routes.navigator
import m.a.nobahar.ui.splash.navigation.routes.screen
import m.a.nobahar.ui.splash.screen.SplashScreen
import m.a.nobahar.ui.splash.screen.SplashViewModel

fun NavGraphBuilder.splashGraph() {
    SplashRoute.screen(this) {
        val viewModel: SplashViewModel = hiltViewModel()
        val state = viewModel.state.collectAsState().value
        val navigation = LocalNavController.comPilotNavController
        LaunchedEffect(state) {
            if (state is Loaded) {
                navigation.clearBackStack().navigate(
                    HomeRoute.navigator
                )
            }
        }
        SplashScreen(
            state = state,
            modifier = Modifier,
            onRetryClick = {
                viewModel.retryClicked()
            }
        )
    }
}