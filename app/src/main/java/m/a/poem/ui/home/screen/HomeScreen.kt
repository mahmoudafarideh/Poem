package m.a.poem.ui.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.persistentListOf
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.poem.domain.model.Failed
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.Loaded
import m.a.poem.domain.model.Loading
import m.a.poem.domain.model.NotLoaded
import m.a.poem.ui.home.component.HomeAppBar
import m.a.poem.ui.home.component.HomeLoadedScreen
import m.a.poem.ui.home.component.HomeLoadingScreen
import m.a.poem.ui.home.model.CenturyUiModel
import m.a.poem.ui.home.model.HomeUiModel
import m.a.poem.ui.info.navigation.InfoRoute
import m.a.poem.ui.info.navigation.routes.navigator
import m.a.poem.ui.search.navigation.SearchRoute
import m.a.poem.ui.search.navigation.routes.navigator
import m.a.poem.ui.shared.components.FetchingDataFailed
import m.a.poem.ui.shared.model.PoetUiModel
import m.a.poem.ui.shared.ui.SabaPreview
import m.a.poem.ui.shared.ui.scrollShadow
import m.a.poem.ui.theme.PoemThemePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    centuries: LoadableData<HomeUiModel>,
    onCenturyClick: (String) -> Unit,
    onRetryClick: () -> Unit,
    onOmenClick: () -> Unit,
    onPoetClick: (PoetUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val navigation = LocalNavController.comPilotNavController
    val onSearchClick = {
        navigation.safeNavigate().navigate(
            SearchRoute(null, null).navigator
        )
    }
    val onInfoClick = {
        navigation.safeNavigate().navigate(InfoRoute.navigator)
    }
    val scrollState = rememberLazyGridState()

    Scaffold(
        topBar = {
            HomeAppBar(
                onInfoClick,
                onOmenClick,
                onSearchClick,
                Modifier.scrollShadow(scrollState)
            )
        },
        modifier = modifier
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            when (centuries) {
                Failed -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        FetchingDataFailed(
                            onRetryClick = onRetryClick
                        )
                    }
                }

                is Loaded<*> -> {
                    centuries.data?.let {
                        HomeLoadedScreen(
                            popularPoets = it.popularPoets,
                            labels = it.labels,
                            poets = it.poets,
                            communication = it.communication,
                            modifier = Modifier,
                            onCenturyClick = onCenturyClick,
                            onPoetClick = onPoetClick,
                            scrollState = scrollState
                        )
                    }
                }

                Loading -> {
                    HomeLoadingScreen()
                }

                NotLoaded -> {}
            }
        }
    }

}

@SabaPreview
@Composable
private fun HomeScreenPreview() {
    PoemThemePreview {
        HomeScreen(
            centuries = Loaded(
                HomeUiModel(
                    popularPoets = persistentListOf(
                        PoetUiModel(
                            "حافظ", "URL", 2
                        ),
                        PoetUiModel(
                            "حافظ", "URL", 3
                        ),
                    ),
                    labels = persistentListOf(
                        CenturyUiModel("قرن پنجم", true),
                        CenturyUiModel("قرن چهارم", false),
                    ),
                    poets = persistentListOf(
                        PoetUiModel(
                            "حافظ", "URL", 2
                        )
                    ),
                )
            ),
            modifier = Modifier,
            onCenturyClick = {},
            onRetryClick = {},
            onPoetClick = {},
            onOmenClick = {},
        )
    }
}

@SabaPreview
@Composable
private fun HomeScreenFailedPreview() {
    PoemThemePreview {
        HomeScreen(
            centuries = Failed,
            modifier = Modifier,
            onCenturyClick = {},
            onRetryClick = {},
            onPoetClick = {},
            onOmenClick = {},
        )
    }
}

@SabaPreview
@Composable
private fun HomeScreenLoadingPreview() {
    PoemThemePreview {
        HomeScreen(
            centuries = Loading,
            modifier = Modifier,
            onCenturyClick = {},
            onRetryClick = {},
            onPoetClick = {},
            onOmenClick = {},
        )
    }
}