package m.a.poem.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.persistentListOf
import m.a.poem.domain.model.Failed
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.Loaded
import m.a.poem.domain.model.Loading
import m.a.poem.domain.model.NotLoaded
import m.a.poem.ui.shared.components.FetchingDataFailed
import m.a.poem.ui.home.component.HomeLoadedScreen
import m.a.poem.ui.home.component.HomeLoadingScreen
import m.a.poem.ui.home.model.CenturyUiModel
import m.a.poem.ui.home.model.HomeUiModel
import m.a.poem.ui.shared.model.PoetUiModel
import m.a.poem.ui.theme.PoemTheme

@Composable
fun HomeScreen(
    centuries: LoadableData<HomeUiModel>,
    onCenturyClick: (String) -> Unit,
    onRetryClick: () -> Unit,
    onPoetClick: (PoetUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                        modifier = Modifier,
                        onCenturyClick = onCenturyClick,
                        onPoetClick = onPoetClick
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

@Preview
@Composable
private fun HomeScreenPreview() {
    PoemTheme {
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
        )
    }
}

@Preview
@Composable
private fun HomeScreenFailedPreview() {
    PoemTheme {
        HomeScreen(
            centuries = Failed,
            modifier = Modifier,
            onCenturyClick = {},
            onRetryClick = {},
            onPoetClick = {},
        )
    }
}