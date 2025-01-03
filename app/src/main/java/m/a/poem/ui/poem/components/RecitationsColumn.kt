package m.a.poem.ui.poem.components

import android.R
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import m.a.poem.domain.model.Loaded
import m.a.poem.ui.poem.model.PoemRecitationUiModel
import m.a.poem.ui.poem.model.PoemUiModel


@Composable
internal fun RecitationsColumn(
    poemUiModel: Loaded<PoemUiModel>,
    onRecitationClicked: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            items = poemUiModel.data.recitations,
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
                                painter = painterResource(R.drawable.ic_media_pause),
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
                                painter = painterResource(R.drawable.ic_media_play),
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