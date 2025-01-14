package m.a.nobahar.ui.search.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
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
import m.a.nobahar.domain.model.Poet
import m.a.nobahar.ui.poem.navigation.PoemRoute
import m.a.nobahar.ui.poem.navigation.routes.navigator
import m.a.nobahar.ui.search.navigation.routes.screen
import m.a.nobahar.ui.search.screen.SearchScreen
import m.a.nobahar.ui.search.screen.SearchViewModel
import m.a.nobahar.ui.toPoetUiModel
import m.a.nobahar.ui.toSearchBookUiModel

fun NavGraphBuilder.searchGraph() {
    SearchRoute.screen(this) {
        val viewModel = searchViewModel(it.argument.poetInfo, it.argument.book)
        val navigation = LocalNavController.comPilotNavController
        val state by viewModel.state.collectAsState()
        SearchScreen(
            state = state,
            onTermChange = { viewModel.termChanged(it) },
            onBackClick = { navigation.safePopBackStack() },
            onPoemClick = { poet, poemId ->
                navigation.safeNavigate().navigate(
                    PoemRoute(poemId = poemId).navigator
                )
            },
            onRetryClick = {
                viewModel.retryClicked()
            },
            onListReachEnd = {
                viewModel.listReachedEnd()
            }
        )
    }
}

@Composable
private fun searchViewModel(
    poet: Poet?,
    book: SearchRoute.Book?
): SearchViewModel {
    val poetUiModel = remember(poet) {
        poet?.toPoetUiModel()
    }
    val bookUiModel = remember(book) {
        book?.toSearchBookUiModel()
    }
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).searchViewModelFactory()

    return viewModel(factory = SearchViewModel.provideFactory(factory, poetUiModel, bookUiModel))
}
