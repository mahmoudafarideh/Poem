package m.a.poem.ui.poem.model

data class PoemRecitationUiModel(
    val artist: String,
    val id: Long,
    val mp3Url: String,
    val state: State,
) {
    sealed class State {
        data class Playing(
            val progress: Long,
            val duration: Long,
        ) : State()

        data object Loading : State()
        data object Paused : State()
        data object None : State()
    }
}