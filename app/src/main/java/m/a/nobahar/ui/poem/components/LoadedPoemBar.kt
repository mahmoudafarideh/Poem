package m.a.nobahar.ui.poem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import m.a.nobahar.ui.poem.model.PoemUiModel
import m.a.nobahar.ui.shared.components.PoetProfilePlaceholder
import m.a.nobahar.ui.shared.components.UrlImage

@Composable
internal fun LoadedPoemBar(
    poemUiModel: PoemUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        UrlImage(
            url = poemUiModel.poetUiModel.profile,
            contentDescription = poemUiModel.poetUiModel.name,
            modifier = Modifier.aspectRatio(.8f),
            placeholder = {
                PoetProfilePlaceholder(poemUiModel.poetUiModel)
            }
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column {
            Text(
                text = poemUiModel.label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = poemUiModel.poetUiModel.nickname + " | " + poemUiModel.bookUiModel.label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}