package m.a.poem.ui.poet.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valentinilk.shimmer.shimmer
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.poem.domain.model.Failed
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.Loaded
import m.a.poem.domain.model.Loading
import m.a.poem.domain.model.NotLoaded
import m.a.poem.ui.book.navigation.BookRoute
import m.a.poem.ui.book.navigation.routes.navigator
import m.a.poem.ui.poet.components.PoetBioLoading
import m.a.poem.ui.poet.components.PoetBooksColumn
import m.a.poem.ui.poet.components.PoetScreenTabs
import m.a.poem.ui.poet.model.PoetScreenTabsUiModel
import m.a.poem.ui.poet.model.PoetScreenUiModel
import m.a.poem.ui.search.navigation.SearchRoute
import m.a.poem.ui.search.navigation.routes.navigator
import m.a.poem.ui.shared.components.FetchingDataFailed
import m.a.poem.ui.shared.components.PoetAppBar
import m.a.poem.ui.shared.model.PoetUiModel
import m.a.poem.ui.shared.ui.LocalWindowSize
import m.a.poem.ui.shared.ui.SabaPreview
import m.a.poem.ui.shared.ui.scrollShadow
import m.a.poem.ui.theme.PoemThemePreview

@Composable
fun PoetScreen(
    poetUiModel: PoetUiModel,
    selectedTab: PoetScreenTabsUiModel,
    onTabClick: (PoetScreenTabsUiModel) -> Unit,
    poetInfo: LoadableData<PoetScreenUiModel.PoetInfo>,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberLazyListState()
    val windowSize = LocalWindowSize.current
    val navigation = LocalNavController.comPilotNavController
    Scaffold(
        topBar = {
            PoetAppBar(
                poetUiModel = poetUiModel,
                onBackClick = { navigation.safePopBackStack() },
                modifier = Modifier.scrollShadow(state),
                onSearchClick = {
                    navigation.safeNavigate().navigate(
                        SearchRoute(poetUiModel.toPoet(), null).navigator
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
                    when (poetInfo) {
                        is Loading -> Modifier.shimmer()
                        else -> Modifier
                    }
                )
                .padding(padding)
        ) {
            PoetScreenTabs(selectedTab, onTabClick)
            when (poetInfo) {
                Failed -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    FetchingDataFailed(
                        onRetryClick = onRetryClick
                    )
                }

                is Loaded -> {
                    if (selectedTab == PoetScreenTabsUiModel.Biography) {
                        Text(
                            text = poetInfo.data.poetBioUiModel.text,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .verticalScroll(rememberScrollState())
                                .padding(
                                    horizontal =
                                        when (windowSize.widthSizeClass) {
                                            WindowWidthSizeClass.Expanded -> 148.dp
                                            else -> 0.dp
                                        }
                                ),
                            lineHeight = 32.sp,
                            textAlign = TextAlign.Justify
                        )
                    } else {
                        PoetBooksColumn(
                            poetInfo,
                            {
                                navigation.safeNavigate().navigate(
                                    BookRoute(
                                        poetInfo = poetUiModel.toPoet(),
                                        bookId = it.id,
                                        bookName = it.label,
                                    ).navigator
                                )
                            },
                            state,
                        )
                    }
                }

                Loading -> PoetBioLoading()

                NotLoaded -> {}
            }
        }
    }
}


@SabaPreview
@Composable
fun PoetScreenPreview() {
    PoemThemePreview {
        PoetScreen(
            poetUiModel = PoetUiModel.fixture,
            modifier = Modifier,
            selectedTab = PoetScreenTabsUiModel.Books,
            onTabClick = {},
            onRetryClick = {},
            poetInfo = Loading,
        )
    }
}