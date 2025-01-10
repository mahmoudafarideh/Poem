package m.a.poem.ui.book.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.valentinilk.shimmer.shimmer
import kotlinx.collections.immutable.ImmutableList
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.poem.domain.model.Failed
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.Loaded
import m.a.poem.domain.model.Loading
import m.a.poem.domain.model.NotLoaded
import m.a.poem.ui.book.components.BookItemsColumn
import m.a.poem.ui.book.components.PoemBioLoading
import m.a.poem.ui.book.model.BookSubItemUiModel
import m.a.poem.ui.book.navigation.BookRoute
import m.a.poem.ui.book.navigation.routes.navigator
import m.a.poem.ui.poem.navigation.PoemRoute
import m.a.poem.ui.poem.navigation.routes.navigator
import m.a.poem.ui.search.navigation.SearchRoute
import m.a.poem.ui.search.navigation.routes.navigator
import m.a.poem.ui.shared.components.FetchingDataFailed
import m.a.poem.ui.shared.components.PoetAppBar
import m.a.poem.ui.shared.model.PoetUiModel
import m.a.poem.ui.shared.ui.SabaPreview
import m.a.poem.ui.shared.ui.scrollShadow
import m.a.poem.ui.theme.PoemThemePreview

@Composable
fun BookScreen(
    poetUiModel: PoetUiModel,
    bookInfo: LoadableData<ImmutableList<BookSubItemUiModel>>,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
    bookId: Long,
    bookName: String
) {
    val state = rememberLazyListState()
    val navigation = LocalNavController.comPilotNavController
    Scaffold(
        topBar = {
            PoetAppBar(
                poetUiModel = poetUiModel,
                onBackClick = { navigation.safePopBackStack() },
                modifier = Modifier.scrollShadow(state),
                onSearchClick = {
                    navigation.safeNavigate().navigate(
                        SearchRoute(
                            poetUiModel.toPoet(),
                            SearchRoute.Book(bookId, bookName)
                        ).navigator
                    )
                },
            )
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    when (bookInfo) {
                        is Loading -> Modifier.shimmer()
                        else -> Modifier
                    }
                )
                .padding(padding)
        ) {
            when (bookInfo) {
                Failed -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    FetchingDataFailed(onRetryClick = onRetryClick)
                }

                is Loaded -> {
                    BookItemsColumn(
                        poetInfo = bookInfo,
                        onBookClick = {
                            navigation.safeNavigate().navigate(
                                BookRoute(
                                    poetInfo = poetUiModel.toPoet(),
                                    bookId = it.id,
                                    bookName = it.label,
                                ).navigator
                            )
                        },
                        onPoemClick = {
                            navigation.safeNavigate().navigate(
                                PoemRoute(
                                    poetInfo = poetUiModel.toPoet(),
                                    poemId = it.id
                                ).navigator
                            )
                        },
                        modifier = Modifier,
                        state = state
                    )
                }

                Loading -> PoemBioLoading(modifier = Modifier)

                NotLoaded -> {}
            }
        }
    }
}


@SabaPreview
@Composable
fun BookScreenPreview() {
    PoemThemePreview {
        BookScreen(
            poetUiModel = PoetUiModel.fixture,
            bookInfo = Loading,
            onRetryClick = {},
            bookId = 1,
            bookName = "bookName",
        )
    }
}