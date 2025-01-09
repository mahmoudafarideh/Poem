package m.a.poem.domain.model

sealed class MediaPlayerState {

    abstract val id: Long
    abstract val label: String
    abstract val poemExcerpt: String

    data class Paused(
        override val poemExcerpt: String,
        override val label: String,
        override val id: Long,
    ): MediaPlayerState()

    data class Loading(
        override val poemExcerpt: String,
        override val label: String,
        override val id: Long,
    ): MediaPlayerState()

    data class Playing(
        override val poemExcerpt: String,
        override val label: String,
        override val id: Long,
        val playingVerseIndex: Int?,
    ): MediaPlayerState()

    data class Ended(
        override val poemExcerpt: String,
        override val label: String,
        override val id: Long,
    ): MediaPlayerState()

    data object LoadingFailed: MediaPlayerState() {
        override val id: Long = 0
        override val label: String = ""
        override val poemExcerpt: String = ""
    }
}