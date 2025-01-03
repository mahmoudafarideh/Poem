package m.a.poem.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import m.a.poem.ui.home.routes.screen

fun NavGraphBuilder.homeGraph() {
    HomeRoute.screen(this) {
        val viewModel: HomeViewModel = viewModel()
        val state by viewModel.state.collectAsState()
        HomeScreen(
            centuries = state,
            modifier = Modifier.fillMaxSize()
        )
    }
}