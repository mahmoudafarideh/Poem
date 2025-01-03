package m.a.poem.ui.home.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import m.a.poem.R
import m.a.poem.ui.home.model.CenturyUiModel
import m.a.poem.ui.shared.model.PoetUiModel
import m.a.poem.ui.theme.PoemThemePreview

@Composable
fun HomeLoadedScreen(
    popularPoets: ImmutableList<PoetUiModel>,
    labels: ImmutableList<CenturyUiModel>,
    poets: ImmutableList<PoetUiModel>,
    onCenturyClick: (String) -> Unit,
    onPoetClick: (PoetUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(3)
    ) {
        item {
            Spacer(
                Modifier.windowInsetsBottomHeight(
                    WindowInsets.systemBars
                )
            )
        }
        item(
            span = { GridItemSpan(3) }
        ) {
            Column {
                Spacer(modifier = Modifier.size(24.dp))
                Text(
                    text = stringResource(R.string.popular_poets_title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 24.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        items(
            items = popularPoets,
            key = { "Popular${it.id}" }
        ) {
            PoetBox(
                poetUiModel = it,
                modifier = Modifier
                    .padding(12.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onPoetClick(it) }
                    .padding(12.dp)
            )
        }

        item(
            span = { GridItemSpan(3) }
        ) {
            Column {
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = stringResource(R.string.categorize_by_century_title),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 24.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.size(16.dp))
                CenturyChips(
                    labels = labels,
                    modifier = Modifier,
                    onClick = onCenturyClick
                )
            }
        }

        items(
            items = poets,
            key = { "${it.id}" }
        ) {
            PoetBox(
                poetUiModel = it,
                modifier = Modifier
                    .padding(12.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onPoetClick(it) }
                    .padding(12.dp)
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
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
            onCenturyClick = {},
            onPoetClick = {},
        )
    }
}