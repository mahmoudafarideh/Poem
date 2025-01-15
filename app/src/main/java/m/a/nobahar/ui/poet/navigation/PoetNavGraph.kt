package m.a.nobahar.ui.poet.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import dagger.hilt.android.EntryPointAccessors
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.nobahar.di.ViewModelFactoryProvider
import m.a.nobahar.domain.model.Loading
import m.a.nobahar.domain.model.Poet
import m.a.nobahar.ui.poem.navigation.PoemRoute
import m.a.nobahar.ui.poem.navigation.routes.navigator
import m.a.nobahar.ui.poet.navigation.routes.screen
import m.a.nobahar.ui.poet.screen.PoetScreen
import m.a.nobahar.ui.poet.screen.PoetViewModel
import m.a.nobahar.ui.toPoetUiModel

fun NavGraphBuilder.poetGraph() {
    PoetRoute.screen(this) {
        val viewModel = poetViewModel(it.argument.poetInfo)
        val state by viewModel.state.collectAsState()
        ObserveRandomPoem(viewModel)
        PoetScreen(
            poetUiModel = state.poet,
            selectedTab = state.selectedTabsUiModel,
            onTabClick = { tabsUiModel ->
                viewModel.tabClicked(tabsUiModel)
            },
            poetInfo = state.poetInfo,
            onRetryClick = {
                viewModel.retryClicked()
            },
            loadingRandomPoem = state.randomPoem is Loading,
            onRandomPoemClick = {
                viewModel.randomPoemClicked()
            }
        )
    }
}

@Composable
private fun ObserveRandomPoem(viewModel: PoetViewModel) {
    val navigation = LocalNavController.comPilotNavController
    LaunchedEffect(Unit) {
        viewModel.randomPoemFLow.collect {
            navigation.safeNavigate().navigate(PoemRoute(it).navigator)
        }
    }
}

@Composable
private fun poetViewModel(poet: Poet): PoetViewModel {
    val poetUiModel = remember(poet) {
        poet.toPoetUiModel()
    }
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).poetViewModelFactory()

    return viewModel(factory = PoetViewModel.provideFactory(factory, poetUiModel))
}
