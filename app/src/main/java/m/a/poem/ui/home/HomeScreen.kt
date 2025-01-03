package m.a.poem.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.collections.immutable.persistentListOf
import m.a.poem.domain.model.Failed
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.Loaded
import m.a.poem.domain.model.Loading
import m.a.poem.domain.model.NotLoaded
import m.a.poem.ui.home.component.HomeLoadedScreen
import m.a.poem.ui.home.model.CenturyUiModel
import m.a.poem.ui.home.model.HomeUiModel
import m.a.poem.ui.home.model.PoetUiModel
import m.a.poem.ui.theme.PoemTheme

@Composable
fun HomeScreen(
    centuries: LoadableData<HomeUiModel>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (centuries) {
            Failed -> {

            }

            is Loaded<*> -> {
                centuries.data?.let {
                    HomeLoadedScreen(
                        popularPoets = it.popularPoets,
                        labels = it.labels,
                        poets = it.poets,
                        modifier = Modifier
                    )
                }
            }

            Loading -> {

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
        )
    }
}