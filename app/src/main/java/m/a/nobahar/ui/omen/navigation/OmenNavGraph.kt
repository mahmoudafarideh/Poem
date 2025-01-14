package m.a.nobahar.ui.omen.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.nobahar.ui.omen.navigation.routes.screen
import m.a.nobahar.ui.omen.screen.OmenScreen
import m.a.nobahar.ui.omen.screen.OmenViewModel
import m.a.nobahar.ui.poem.navigation.PoemRoute
import m.a.nobahar.ui.poem.navigation.routes.navigator

fun NavGraphBuilder.omenGraph() {
    OmenRoute.screen(this) {
        val viewModel: OmenViewModel = hiltViewModel()
        val state = viewModel.state.collectAsStateWithLifecycle().value
        val navigation = LocalNavController.comPilotNavController
        LaunchedEffect(state) {
            state.data?.let {
                navigation.safeNavigate().navigate(
                    PoemRoute(it.poemId).navigator
                )
                viewModel.navigatedToPoemScreen()
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            OmenScreen(
                state = state,
                onRetryClick = {
                    viewModel.retryClicked()
                },
                onErrorDismiss = {
                    viewModel.errorDismissed()
                },
                onOmenClick = {
                    viewModel.omenClicked()
                },
            )
        }
    }
}