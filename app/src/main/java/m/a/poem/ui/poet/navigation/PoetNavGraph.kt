package m.a.poem.ui.poet.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import dagger.hilt.android.EntryPointAccessors
import m.a.poem.di.ViewModelFactoryProvider
import m.a.poem.domain.model.Poet
import m.a.poem.ui.poet.navigation.routes.screen
import m.a.poem.ui.poet.screen.PoetScreen
import m.a.poem.ui.poet.screen.PoetViewModel
import m.a.poem.ui.shared.model.PoetUiModel

fun NavGraphBuilder.poetGraph() {
    PoetRoute.screen(this) {
        val viewModel = poetViewModel(it.argument.poetInfo)
        val state by viewModel.state.collectAsState()
        PoetScreen(
            poetUiModel = state.poet,
            selectedTab = state.selectedTabsUiModel,
            onTabClick = { tabsUiModel ->
                viewModel.tabClicked(tabsUiModel)
            },
            poetInfo = state.poetInfo,
            onRetryClick = {
                viewModel.retryClicked()
            }
        )
    }
}

@Composable
private fun poetViewModel(poet: Poet): PoetViewModel {
    val poetUiModel = remember(poet) {
        PoetUiModel(
            name = poet.name,
            nickname = poet.nickName,
            profile = poet.profile,
            id = poet.id
        )
    }
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).poetViewModelFactory()

    return viewModel(factory = PoetViewModel.provideFactory(factory, poetUiModel))
}
