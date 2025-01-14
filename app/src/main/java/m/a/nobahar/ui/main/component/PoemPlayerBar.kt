package m.a.nobahar.ui.main.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import m.a.nobahar.ui.main.model.MediaPlayerUiModel
import m.a.nobahar.ui.shared.ui.SabaPreview
import m.a.nobahar.ui.theme.PoemThemePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PoemPlayerBar(
    state: MediaPlayerUiModel?,
    onCloseClick: () -> Unit,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.animateContentSize()) {
        state?.let {
            TopAppBar(
                title = { PlayingPoemTitle(state.title, state.subTitle) },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    when (state.state) {
                        MediaPlayerUiModel.State.Playing -> PlayerActionIcon(
                            Icons.Default.Pause, onPauseClick
                        )

                        MediaPlayerUiModel.State.Loading -> CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(12.dp)
                                .size(24.dp)
                        )

                        MediaPlayerUiModel.State.Paused -> PlayerActionIcon(
                            Icons.Default.PlayArrow, onPlayClick
                        )
                    }
                },
                actions = {
                    ClosePlayerIcon(onCloseClick)
                },
            )
        }

    }
}

@SabaPreview
@Composable
private fun PoemPlayerBarPreview() {
    PoemThemePreview {
        Column {
            PoemPlayerBar(
                state = MediaPlayerUiModel(
                    title = "الا یا ایها الساقی ادر کأسا و ناولها",
                    subTitle = "فریدون فرح‌اندوز",
                    state = MediaPlayerUiModel.State.Loading
                ),
                onCloseClick = {},
                onPauseClick = {},
                onPlayClick = {},
            )
            PoemPlayerBar(
                state = MediaPlayerUiModel(
                    title = "الا یا ایها الساقی ادر کأسا و ناولها",
                    subTitle = "فریدون فرح‌اندوز",
                    state = MediaPlayerUiModel.State.Playing
                ),
                onCloseClick = {},
                onPauseClick = {},
                onPlayClick = {},
            )
        }
    }
}