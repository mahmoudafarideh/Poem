package m.a.poem.ui.poet.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
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
import m.a.poem.domain.model.Poet
import m.a.poem.ui.book.navigation.BookRoute
import m.a.poem.ui.book.navigation.routes.navigator
import m.a.poem.ui.poet.components.PoetBioLoading
import m.a.poem.ui.poet.components.PoetBooksColumn
import m.a.poem.ui.poet.components.PoetScreenTabs
import m.a.poem.ui.poet.model.PoetScreenTabsUiModel
import m.a.poem.ui.poet.model.PoetScreenUiModel
import m.a.poem.ui.shared.components.FetchingDataFailed
import m.a.poem.ui.shared.components.PoetAppBar
import m.a.poem.ui.shared.model.PoetUiModel
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
                                modifier = Modifier.padding(horizontal = 24.dp),
                                lineHeight = 32.sp,
                                textAlign = TextAlign.Justify
                            )
                        } else {
                            PoetBooksColumn(
                                poetInfo,
                                {
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
                                }
                            )
                        }
                    }

                    Loading -> PoetBioLoading()

                    NotLoaded -> {}
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
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