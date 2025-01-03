package m.a.poem.ui.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import m.a.poem.ui.home.model.CenturyUiModel
import m.a.poem.ui.home.model.PoetUiModel
import m.a.poem.ui.theme.PoemThemePreview

@Composable
fun HomeLoadedScreen(
    popularPoets: ImmutableList<PoetUiModel>,
    labels: ImmutableList<CenturyUiModel>,
    poets: ImmutableList<PoetUiModel>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(24.dp))
        Text(
            text = "سخنوران پرمخاطب",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 24.dp)
        )
        PoetsGrid(popularPoets)
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "دسته‌بندی براساس قرن",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 24.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        CenturyChips(
            labels = labels,
            modifier = Modifier
        )
        PoetsGrid(poets)
    }
}

@Composable
private fun PoetsGrid(
    popularPoets: ImmutableList<PoetUiModel>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        userScrollEnabled = false,
    ) {
        items(
            items = popularPoets,
            key = { it.id }
        ) {
            PoetBox(
                poetUiModel = it,
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}

@Preview
@Composable
private fun HomeLoadedScreenPreview() {
    PoemThemePreview {
        HomeLoadedScreen(
            popularPoets = persistentListOf(
                PoetUiModel("حافظ", "URL", 2),
                PoetUiModel("سعدی", "URL", 3),
                PoetUiModel("مولانا", "URL", 4),
                PoetUiModel("فردوسی", "URL", 5),
                PoetUiModel("خیام", "URL", 6),
                PoetUiModel("نظامی", "URL", 7),
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
    }
}