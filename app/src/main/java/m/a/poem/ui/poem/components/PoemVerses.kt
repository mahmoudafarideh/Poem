package m.a.poem.ui.poem.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import m.a.poem.domain.model.Loaded
import m.a.poem.ui.book.model.PoemItemUiModel
import m.a.poem.ui.poem.model.PoemUiModel
import m.a.poem.ui.poem.model.PoemVerseUiModel
import m.a.poem.ui.shared.ui.LocalWindowSize

@Composable
internal fun PoemVerses(
    poemUiModel: Loaded<PoemUiModel>,
    onPoemClick: (Long) -> Unit,
    state: LazyListState,
    modifier: Modifier = Modifier
) {
    val windowSize = LocalWindowSize.current
    val highlightColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = .5f)
    LazyColumn(
        modifier = modifier.padding(
            horizontal = when (windowSize.widthSizeClass) {
                WindowWidthSizeClass.Expanded -> 148.dp
                else -> 0.dp
            }
        ),
        contentPadding = PaddingValues(vertical = 12.dp),
        state = state
    ) {
        items(
            items = poemUiModel.data.verses,
            key = { it.id }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
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
                val highlightSize = animateFloatAsState(
                    targetValue = if (it.isHighlighted) 1f else 0f,
                    animationSpec = tween(2200),
                )
                Text(
                    text = it.text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .align(
                            when (it.position) {
                                PoemVerseUiModel.VersePosition.Start -> Alignment.CenterStart
                                PoemVerseUiModel.VersePosition.End -> Alignment.CenterEnd
                            }
                        )
                        .padding(horizontal = 12.dp)
                        .drawBehind {
                            highlightVerse(highlightSize, highlightColor)
                        }
                        .padding(horizontal = 12.dp),
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
                                it,
                                onPoemClick, Modifier
                                    .padding(12.dp)
                                    .align(Alignment.CenterEnd)
                            )
                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        poemUiModel.data.next?.let {
                            AnotherPoemCard(
                                it,
                                onPoemClick,
                                Modifier
                                    .padding(12.dp)
                                    .align(Alignment.CenterStart)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun DrawScope.highlightVerse(
    highlightSize: State<Float>,
    highlightColor: Color
) {
    if (highlightSize.value > 0f) {
        drawRect(
            color = highlightColor,
            topLeft = Offset(
                size.width.minus(size.width.times(highlightSize.value)),
                0f,
            ),
            size = Size(
                size.width.minus(size.width.times(1f - highlightSize.value)),
                size.height,
            )
        )
    }
}

@Composable
private fun AnotherPoemCard(
    poem: PoemItemUiModel,
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
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = poem.excerpt,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}