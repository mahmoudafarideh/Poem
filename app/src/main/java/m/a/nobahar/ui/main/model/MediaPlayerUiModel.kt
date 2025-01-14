package m.a.nobahar.ui.main.model

import m.a.nobahar.domain.model.MediaPlayerState

data class MediaPlayerUiModel(
    val title: String,
    val subTitle: String,
    val state: State
) {
    enum class State {
        Playing,
        Loading,
        Paused
    }
}

fun MediaPlayerState.toMediaPlayerUiModel() = this.poemAudioInfo?.let {
    MediaPlayerUiModel(
        title = it.poemInfo.excerpt,
        subTitle = it.recitation.artistName,
        state = when(this) {
            is MediaPlayerState.Loading -> MediaPlayerUiModel.State.Loading
            is MediaPlayerState.Paused -> MediaPlayerUiModel.State.Paused
            else -> MediaPlayerUiModel.State.Playing
        }
    )
}