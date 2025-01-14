package m.a.nobahar.ui.poem.model

import m.a.nobahar.domain.model.PoemRecitation

data class PoemRecitationUiModel(
    val artist: String,
    val id: Long,
    val mp3Url: String,
    val state: State,
    val syncUrl: String? = null,
) {
    sealed class State {
        data object Playing : State()
        data object Loading : State()
        data object Paused : State()
        data object None : State()
    }

    fun toRecitation() = PoemRecitation(artist, id, mp3Url, syncUrl)
}

internal fun PoemRecitation.toPoemRecitationUiModel() = PoemRecitationUiModel(
    artist = artistName,
    mp3Url = mp3Url,
    id = id,
    state = PoemRecitationUiModel.State.None,
    syncUrl = syncUrl
)