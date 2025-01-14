package m.a.nobahar.ui.book.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import m.a.nobahar.domain.model.Loaded
import m.a.nobahar.ui.book.model.BookSubItemUiModel
import m.a.nobahar.ui.book.model.BookItemUiModel
import m.a.nobahar.ui.book.model.PoemItemUiModel
import m.a.nobahar.ui.shared.ui.LocalWindowSize

@Composable
internal fun BookItemsColumn(
    poetInfo: Loaded<ImmutableList<BookSubItemUiModel>>,
    onBookClick: (BookItemUiModel) -> Unit,
    onPoemClick: (PoemItemUiModel) -> Unit,
    state: LazyListState,
    modifier: Modifier = Modifier
) {
    val windowSize = LocalWindowSize.current
    val horizontalPadding = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> 48.dp
        else -> 0.dp
    }
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = state,
    ) {
        items(
            items = poetInfo.data,
            key = { it.itemId }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        when (it) {
                            is BookItemUiModel -> onBookClick(it)
                            is PoemItemUiModel -> onPoemClick(it)
                        }
                    }
                    .padding(horizontal = horizontalPadding)
                    .padding(vertical = 12.dp)
                    .padding(horizontal = 24.dp)
            ) {
                Icon(
                    imageVector = when (it) {
                        is BookItemUiModel -> Icons.Default.Book
                        is PoemItemUiModel -> Icons.Default.Description
                    },
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.size(12.dp))
                when (it) {
                    is BookItemUiModel -> Text(
                        text = it.label,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    is PoemItemUiModel -> {
                        Text(
                            text = it.label + ": ",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        Text(
                            text = it.excerpt,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(vertical = 12.dp),
                            maxLines = 1
                        )
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(start = 56.dp)
            )
        }
    }
}