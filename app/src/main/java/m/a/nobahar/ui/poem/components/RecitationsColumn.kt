package m.a.nobahar.ui.poem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import m.a.nobahar.ui.poem.model.PoemRecitationUiModel

@Composable
internal fun RecitationsColumn(
    recitations: ImmutableList<PoemRecitationUiModel>,
    onRecitationClicked: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            items = recitations,
            key = { it.id }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        onRecitationClicked(it.id)
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(56.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    when (it.state) {
                        is PoemRecitationUiModel.State.Playing -> {
                            Icon(
                                imageVector = Icons.Default.Pause,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = null
                            )
                        }

                        PoemRecitationUiModel.State.Loading -> {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        PoemRecitationUiModel.State.Paused, PoemRecitationUiModel.State.None -> {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = null
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(24.dp))
                Text(
                    text = it.artist,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}