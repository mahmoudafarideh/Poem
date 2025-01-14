package m.a.nobahar.ui.poem.navigation

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import dagger.hilt.android.EntryPointAccessors
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.nobahar.di.ViewModelFactoryProvider
import m.a.nobahar.ui.poem.navigation.routes.navigator
import m.a.nobahar.ui.poem.navigation.routes.screen
import m.a.nobahar.ui.poem.screen.PoemScreen
import m.a.nobahar.ui.poem.screen.PoemViewModel

fun NavGraphBuilder.poemGraph() {
    PoemRoute.screen(this) {
        val viewModel = poemViewModel(it.argument.poemId)
        val state by viewModel.state.collectAsStateWithLifecycle()
        val navigation = LocalNavController.comPilotNavController
        PoemScreen(
            onRetryClick = {
                viewModel.retryClicked()
            },
            poemUiModel = state.poem,
            onPoemClick = { id ->
                navigation.navigate(
                    PoemRoute(id).navigator
                )
            },
            modifier = Modifier.fillMaxSize(),
            onRecitationClicked = {
                viewModel.recitationClicked(it)
            },
            onVerseClick = {
                viewModel.verseClicked(it)
            },
            onVersesCopyClick = {
                viewModel.releaseVerses()
            },
            onVerseArtworkClick = {
                viewModel.releaseVerses()
            }
        )
    }
}

@Composable
private fun poemViewModel(poemId: Long): PoemViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).poemViewModelFactory()

    return viewModel(factory = PoemViewModel.provideFactory(factory, poemId))
}
