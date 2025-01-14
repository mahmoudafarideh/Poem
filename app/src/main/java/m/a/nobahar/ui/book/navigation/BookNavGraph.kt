package m.a.nobahar.ui.book.navigation

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
import m.a.nobahar.di.ViewModelFactoryProvider
import m.a.nobahar.domain.model.Poet
import m.a.nobahar.ui.book.navigation.routes.screen
import m.a.nobahar.ui.book.screen.BookScreen
import m.a.nobahar.ui.book.screen.BookViewModel
import m.a.nobahar.ui.toPoetUiModel

fun NavGraphBuilder.bookGraph() {
    BookRoute.screen(this) {
        val viewModel = bookViewModel(it.argument.poetInfo, it.argument.bookId)
        val state by viewModel.state.collectAsState()
        BookScreen(
            poetUiModel = state.poet,
            onRetryClick = {
                viewModel.retryClicked()
            },
            bookInfo = state.items,
            modifier = Modifier.fillMaxSize(),
            bookId = it.argument.bookId,
            bookName = it.argument.bookName
        )
    }
}

@Composable
private fun bookViewModel(poet: Poet, bookId: Long): BookViewModel {
    val poetUiModel = remember(poet) {
        poet.toPoetUiModel()
    }
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).bookViewModelFactory()

    return viewModel(factory = BookViewModel.provideFactory(factory, poetUiModel, bookId))
}

