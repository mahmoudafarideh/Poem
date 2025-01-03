package m.a.poem.ui.book.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import com.valentinilk.shimmer.shimmer
import kotlinx.collections.immutable.ImmutableList
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.poem.domain.model.Failed
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.Loaded
import m.a.poem.domain.model.Loading
import m.a.poem.domain.model.NotLoaded
import m.a.poem.domain.model.Poet
import m.a.poem.ui.book.components.BookItemsColumn
import m.a.poem.ui.book.components.PoemBioLoading
import m.a.poem.ui.book.model.BookSubItemUiModel
import m.a.poem.ui.book.navigation.BookRoute
import m.a.poem.ui.book.navigation.routes.navigator
import m.a.poem.ui.poem.navigation.PoemRoute
import m.a.poem.ui.poem.navigation.routes.navigator
import m.a.poem.ui.shared.components.FetchingDataFailed
import m.a.poem.ui.shared.components.PoetAppBar
import m.a.poem.ui.shared.model.PoetUiModel
import m.a.poem.ui.theme.PoemThemePreview

@Composable
fun BookScreen(
    poetUiModel: PoetUiModel,
    bookInfo: LoadableData<ImmutableList<BookSubItemUiModel>>,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navigation = LocalNavController.comPilotNavController
    Scaffold(
        topBar = {
            PoetAppBar(
                poetUiModel = poetUiModel,
                onBackClick = { navigation.safePopBackStack() },
                modifier = Modifier
            )
        },
        modifier = modifier
    ) { padding ->
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
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
                                        poetInfo = Poet(
                                            id = poetUiModel.id,
                                            name = poetUiModel.name,
                                            nickName = poetUiModel.nickname,
                                            profile = poetUiModel.profile,
                                        ),
                                        bookId = it.id
                                    ).navigator
                                )
                            },
                            onPoemClick = {
                                navigation.safeNavigate().navigate(
                                    PoemRoute(
                                        poetInfo = Poet(
                                            id = poetUiModel.id,
                                            name = poetUiModel.name,
                                            nickName = poetUiModel.nickname,
                                            profile = poetUiModel.profile,
                                        ),
                                        poemId = it.id
                                    ).navigator
                                )
                            }
                        )
                    }

                    Loading -> PoemBioLoading()

                    NotLoaded -> {}
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BookScreenPreview() {
    PoemThemePreview {
        BookScreen(
            poetUiModel = PoetUiModel.fixture,
            modifier = Modifier,
            bookInfo = Loading,
            onRetryClick = {},
        )
    }
}