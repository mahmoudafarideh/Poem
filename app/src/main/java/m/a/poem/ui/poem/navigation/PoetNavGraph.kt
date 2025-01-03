package m.a.poem.ui.poem.navigation

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import dagger.hilt.android.EntryPointAccessors
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.poem.di.ViewModelFactoryProvider
import m.a.poem.domain.model.Poet
import m.a.poem.ui.poem.navigation.routes.navigator
import m.a.poem.ui.poem.navigation.routes.screen
import m.a.poem.ui.poem.screen.PoemScreen
import m.a.poem.ui.poem.screen.PoemViewModel
import m.a.poem.ui.shared.model.PoetUiModel

fun NavGraphBuilder.poemGraph() {
    PoemRoute.screen(this) {
        val viewModel = poemViewModel(it.argument.poetInfo, it.argument.poemId)
        val state by viewModel.state.collectAsState()
        val navigation = LocalNavController.comPilotNavController
        PoemScreen(
            poetUiModel = state.poet,
            onRetryClick = {
                viewModel.retryClicked()
            },
            poemUiModel = state.poem,
            onPoemClick = { id ->
                navigation.navigate(
                    PoemRoute(it.argument.poetInfo, id).navigator
                )
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun poemViewModel(poet: Poet, bookId: Long): PoemViewModel {
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
    ).poemViewModelFactory()

    return viewModel(factory = PoemViewModel.provideFactory(factory, poetUiModel, bookId))
}
