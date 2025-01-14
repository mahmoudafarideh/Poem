package m.a.nobahar.domain.model

sealed class MediaPlayerState {

    abstract val poemAudioInfo: PoemAudioInfo?

    data class Paused(
        override val poemAudioInfo: PoemAudioInfo,
    ): MediaPlayerState()

    data class Loading(
        override val poemAudioInfo: PoemAudioInfo,
    ): MediaPlayerState()

    data class Playing(
        override val poemAudioInfo: PoemAudioInfo,
        val playingVerseIndex: Int?,
    ): MediaPlayerState()

    data object Ended: MediaPlayerState() {
        override val poemAudioInfo: PoemAudioInfo? = null
    }

    data object LoadingFailed: MediaPlayerState() {
        override val poemAudioInfo: PoemAudioInfo? = null
    }
}