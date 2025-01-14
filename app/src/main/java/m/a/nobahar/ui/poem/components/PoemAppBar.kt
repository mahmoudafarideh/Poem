package m.a.nobahar.ui.poem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import m.a.nobahar.domain.model.LoadableData
import m.a.nobahar.domain.model.Loaded
import m.a.nobahar.domain.model.Loading
import m.a.nobahar.ui.poem.model.PoemUiModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun PoemAppBar(
    poemUiModel: LoadableData<PoemUiModel>,
    onBackClick: () -> Unit,
    onCopyClick: () -> Unit,
    onArtworkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            poemUiModel.data?.let {
                LoadedPoemBar(it, Modifier.padding(vertical = 4.dp))
            }
            when (poemUiModel) {
                is Loaded<*> -> {
                    poemUiModel.data?.let {
                        LoadedPoemBar(it, Modifier.padding(vertical = 4.dp))
                    }
                }

                Loading -> {
                    LoadingPoemBar(
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }

                else -> {}
            }
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onBackClick()
                    }
                    .padding(12.dp)
            )
        },
        modifier = modifier,
        actions = {
            poemUiModel.data?.let {
                Icon(
                    imageVector = Icons.Outlined.Photo,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onArtworkClick() }
                        .graphicsLayer {
                            alpha = if (it.isOneVerseSelected) 1f else .3f
                        }
                        .padding(12.dp)
                )
                Icon(
                    imageVector = Icons.Outlined.ContentCopy,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onCopyClick() }
                        .graphicsLayer {
                            alpha = if (it.anyVerseSelected) 1f else .3f
                        }
                        .padding(12.dp)
                )
            }
        }
    )
}

