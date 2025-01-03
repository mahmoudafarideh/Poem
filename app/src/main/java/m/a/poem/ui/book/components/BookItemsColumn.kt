package m.a.poem.ui.book.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import m.a.poem.R
import m.a.poem.domain.model.Loaded
import m.a.poem.ui.book.model.BookSubItemUiModel
import m.a.poem.ui.book.model.SubBook
import m.a.poem.ui.book.model.SubPoem

@Composable
internal fun BookItemsColumn(
    poetInfo: Loaded<ImmutableList<BookSubItemUiModel>>,
    onBookClick: (SubBook) -> Unit,
    onPoemClick: (SubPoem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
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
                            is SubBook -> onBookClick(it)
                            is SubPoem -> onPoemClick(it)
                        }
                    }
                    .padding(vertical = 12.dp)
                    .padding(horizontal = 24.dp)
            ) {
                Icon(
                    painter = painterResource(
                        when (it) {
                            is SubBook -> R.drawable.agenda
                            is SubPoem -> R.drawable.poem
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.size(12.dp))
                when (it) {
                    is SubBook -> Text(
                        text = it.label,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    is SubPoem -> {
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