package m.a.poem.ui.home.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.poem.ui.home.navigation.routes.screen
import m.a.poem.ui.home.screen.HomeScreen
import m.a.poem.ui.home.screen.HomeViewModel
import m.a.poem.ui.omen.navigation.OmenRoute
import m.a.poem.ui.omen.navigation.routes.navigator
import m.a.poem.ui.poet.navigation.PoetRoute
import m.a.poem.ui.poet.navigation.routes.navigator

fun NavGraphBuilder.homeGraph() {
    HomeRoute.screen(this) {
        val viewModel: HomeViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        val navigation = LocalNavController.comPilotNavController
        HomeScreen(
            centuries = state,
            modifier = Modifier,
            onCenturyClick = {
                viewModel.centuryClicked(it)
            },
            onRetryClick = {
                viewModel.retryClicked()
            },
            onPoetClick = {
                navigation
                    .safeNavigate()
                    .navigate(PoetRoute(it.toPoet()).navigator)
            },
            onOmenClick = {
                navigation.safeNavigate().navigate(OmenRoute.navigator)
            }
        )
    }
}