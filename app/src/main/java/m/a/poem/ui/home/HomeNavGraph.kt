package m.a.poem.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.poem.domain.model.Poet
import m.a.poem.ui.home.routes.screen
import m.a.poem.ui.poet.navigation.PoetRoute
import m.a.poem.ui.poet.navigation.routes.navigator

fun NavGraphBuilder.homeGraph() {
    HomeRoute.screen(this) {
        val viewModel: HomeViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        val navigation = LocalNavController.comPilotNavController
        HomeScreen(
            centuries = state,
            modifier = Modifier.fillMaxSize(),
            onCenturyClick = {
                viewModel.centuryClicked(it)
            },
            onRetryClick = {
                viewModel.retryClicked()
            },
            onPoetClick = {
                navigation
                    .safeNavigate()
                    .navigate(
                        PoetRoute(
                            Poet(
                                id = it.id,
                                name = it.name,
                                profile = it.profile,
                                nickName = it.nickname
                            )
                        ).navigator
                    )
            }
        )
    }
}