package m.a.poem.ui.poem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import m.a.poem.domain.model.Loaded
import m.a.poem.ui.book.model.SubPoem
import m.a.poem.ui.poem.model.PoemUiModel
import m.a.poem.ui.poem.model.PoemVerseUiModel

@Composable
internal fun PoemVerses(
    poemUiModel: Loaded<PoemUiModel>,
    onPoemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        items(
            items = poemUiModel.data.verses,
            key = { it.id }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(
                        top = when (it.position) {
                            PoemVerseUiModel.VersePosition.Start -> 12.dp
                            PoemVerseUiModel.VersePosition.End -> 12.dp
                        },
                        bottom = when (it.position) {
                            PoemVerseUiModel.VersePosition.Start -> 0.dp
                            PoemVerseUiModel.VersePosition.End -> 12.dp
                        }
                    )
            ) {
                Text(
                    text = it.text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .align(
                            when (it.position) {
                                PoemVerseUiModel.VersePosition.Start -> Alignment.CenterStart
                                PoemVerseUiModel.VersePosition.End -> Alignment.CenterEnd
                            }
                        ),
                    color = MaterialTheme.colorScheme.onBackground,
                    minLines = 1
                )
            }
            when (it.position) {
                PoemVerseUiModel.VersePosition.Start -> {}
                PoemVerseUiModel.VersePosition.End -> {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }
        item {
            if (poemUiModel.data.next != null || poemUiModel.data.previous != null) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f)) {
                        poemUiModel.data.previous?.let {
                            AnotherPoemCard(
                                it, onPoemClick, Modifier.padding(12.dp)
                            )
                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        poemUiModel.data.next?.let {
                            AnotherPoemCard(
                                it, onPoemClick, Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnotherPoemCard(
    poem: SubPoem,
    onPoemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondaryContainer),
        onClick = {
            onPoemClick(poem.id)
        }
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = poem.label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = poem.excerpt,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 1
            )
        }
    }
}