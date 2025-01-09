package m.a.poem.ui.main.component

import android.app.Activity
import android.view.WindowManager
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import m.a.poem.domain.model.MediaPlayerState
import m.a.poem.ui.main.PoemPlayerViewModel
import m.a.poem.ui.shared.ui.SabaPreview
import m.a.poem.ui.theme.PoemThemePreview


@Composable
internal fun PoemPlayerBar(
    activity: Activity,
    modifier: Modifier = Modifier
) {
    val poemPlayerViewModel: PoemPlayerViewModel = hiltViewModel()
    val state = poemPlayerViewModel.state.collectAsState().value
    PoemPlayerBar(
        state = state,
        onCloseClick = {
            poemPlayerViewModel.closeClicked()
        },
        onPlayClick = {
            poemPlayerViewModel.playClicked()
        },
        onPauseClick = {
            poemPlayerViewModel.pauseClicked()
        },
        modifier = modifier
    )
    LaunchedEffect(state) {
        when (state) {
            is MediaPlayerState.Playing -> {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }

            else -> {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PoemPlayerBar(
    state: MediaPlayerState?,
    onCloseClick: () -> Unit,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.animateContentSize()) {
        when (state) {
            is MediaPlayerState.Loading -> {
                TopAppBar(
                    title = {
                        PlayingPoemTitle(state)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    navigationIcon = {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(12.dp)
                                .size(24.dp)
                        )
                    },
                    actions = {
                        ClosePlayerIcon(onCloseClick)
                    }
                )
            }

            is MediaPlayerState.Paused -> {
                TopAppBar(
                    title = {
                        PlayingPoemTitle(state)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    navigationIcon = {
                        PlayerActionIcon(
                            Icons.Default.PlayArrow,
                            onPlayClick
                        )
                    },
                    actions = {
                        ClosePlayerIcon(onCloseClick)
                    },
                )
            }

            is MediaPlayerState.Playing -> {
                TopAppBar(
                    title = {
                        PlayingPoemTitle(state)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    navigationIcon = {
                        PlayerActionIcon(
                            Icons.Default.Pause,
                            onPauseClick
                        )
                    },
                    actions = {
                        ClosePlayerIcon(onCloseClick)
                    }
                )
            }

            else -> {

            }
        }

    }
}

@Composable
private fun PlayerActionIcon(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = icon,
        tint = MaterialTheme.colorScheme.primary,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(12.dp)
    )
}

@Composable
private fun ClosePlayerIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Default.Close,
        tint = MaterialTheme.colorScheme.primary,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .padding(12.dp)
    )
}

@Composable
private fun PlayingPoemTitle(
    state: MediaPlayerState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = state.poemExcerpt,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1
        )
        Text(
            text = state.label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1
        )
    }
}

@SabaPreview
@Composable
private fun PoemPlayerBarPreview() {
    PoemThemePreview {
        Column {
            PoemPlayerBar(
                state = MediaPlayerState.Loading(
                    poemExcerpt = "الا یا ایها الساقی ادر کأسا و ناولها",
                    label = "فریدون فرح‌اندوز",
                    id = 2840
                ),
                onCloseClick = {},
                onPauseClick = {},
                onPlayClick = {},
            )
            PoemPlayerBar(
                state = MediaPlayerState.Playing(
                    poemExcerpt = "الا یا ایها الساقی ادر کأسا و ناولها",
                    label = "فریدون فرح‌اندوز",
                    id = 2840,
                    playingVerseIndex = 1
                ),
                onCloseClick = {},
                onPauseClick = {},
                onPlayClick = {},
            )
        }
    }
}