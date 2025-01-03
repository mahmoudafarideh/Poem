package m.a.poem.ui.book.navigation

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
import m.a.poem.di.ViewModelFactoryProvider
import m.a.poem.domain.model.Poet
import m.a.poem.ui.book.navigation.routes.screen
import m.a.poem.ui.book.screen.BookScreen
import m.a.poem.ui.book.screen.BookViewModel
import m.a.poem.ui.shared.model.PoetUiModel

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
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun bookViewModel(poet: Poet, bookId: Long): BookViewModel {
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
    ).bookViewModelFactory()

    return viewModel(factory = BookViewModel.provideFactory(factory, poetUiModel, bookId))
}

