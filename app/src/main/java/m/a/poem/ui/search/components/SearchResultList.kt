package m.a.poem.ui.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import m.a.poem.domain.model.LoadingMore
import m.a.poem.domain.model.LoadingMoreFailed
import m.a.poem.domain.model.PaginateLoadableData
import m.a.poem.ui.book.model.BookItemUiModel
import m.a.poem.ui.book.model.PoemItemUiModel
import m.a.poem.ui.search.model.SearchResultUiModel
import m.a.poem.ui.shared.components.FetchingDataFailed
import m.a.poem.ui.shared.components.PoetProfilePlaceholder
import m.a.poem.ui.shared.components.UrlImage
import m.a.poem.ui.shared.model.PoetUiModel
import m.a.poem.ui.shared.ui.LocalWindowSize
import m.a.poem.ui.shared.ui.SabaPreview
import m.a.poem.ui.theme.PoemThemePreview

@Composable
internal fun SearchResultList(
    result: ImmutableList<SearchResultUiModel>,
    onClick: (poet: PoetUiModel, poem: Long) -> Unit,
    resultState: PaginateLoadableData<SearchResultUiModel>,
    onListReachEnd: () -> Unit,
    onRetryClick: () -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier,
) {
    val windowSize = LocalWindowSize.current
    val horizontalPadding = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> 48.dp
        else -> 0.dp
    }

    val endOfListReached by remember {
        derivedStateOf {
            listState.isScrolledToEnd()
        }
    }

    LaunchedEffect(endOfListReached) {
        onListReachEnd()
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = listState
    ) {
        items(
            items = result,
            key = { it.poemUiModel.id }
        ) {
            SearchPoemRow(
                it,
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(it.poetUiModel, it.poemUiModel.id)
                    }
                    .padding(horizontal = horizontalPadding)
                    .padding(vertical = 12.dp)
                    .padding(horizontal = 24.dp)
            )
        }
        item {
            if (resultState is LoadingMore) {
                SearchPoemShimmer(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalPadding)
                        .padding(vertical = 12.dp)
                        .padding(horizontal = 24.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.padding(start = 56.dp)
                )
            } else if (resultState is LoadingMoreFailed) {
                FetchingDataFailed(
                    onRetryClick = onRetryClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
            }
        }
    }
}


private fun LazyListState.isScrolledToEnd(): Boolean =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

@Composable
private fun SearchPoemRow(
    model: SearchResultUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Box(modifier = Modifier.width(48.dp)) {
            UrlImage(
                url = model.poetUiModel.profile,
                contentDescription = model.poetUiModel.name,
                modifier = Modifier.aspectRatio(.8f),
                placeholder = {
                    PoetProfilePlaceholder(model.poetUiModel)
                }
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Column {
            Text(
                text = model.poemUiModel.excerpt,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                modifier = Modifier.padding(vertical = 4.dp),
            )
            Text(
                text = model.poetUiModel.name + when {
                    model.bookUiModel.label.isNotEmpty() -> " | " + model.bookUiModel.label
                    else -> ""
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = 4.dp),
            )
            Text(
                text = model.poemUiModel.label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = 4.dp),
                maxLines = 1
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(start = 56.dp)
    )
}

@SabaPreview
@Composable
fun SearchResultListPreview() {
    PoemThemePreview {
        SearchResultList(
            result = persistentListOf(
                SearchResultUiModel(
                    poemUiModel = PoemItemUiModel(
                        "غزل شماره یک",
                        "الا یا ایها الساقی",
                        1
                    ),
                    poetUiModel = PoetUiModel.fixture,
                    bookUiModel = BookItemUiModel(
                        "غزلیات حافظ",
                        1
                    )
                )
            ),
            onClick = { _, _ -> },
            resultState = LoadingMore(persistentListOf(), 0, 1),
            onListReachEnd = {},
            onRetryClick = {},
            listState = rememberLazyListState()
        )
    }
}