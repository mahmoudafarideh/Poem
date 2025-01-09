package m.a.poem.domain.repository

import kotlinx.coroutines.flow.StateFlow
import m.a.poem.domain.model.MediaPlayerState
import m.a.poem.domain.model.PoemAudioInfo

interface MediaPlayerRepository {
    val state: StateFlow<MediaPlayerState?>
    fun play(poemAudioInfo: PoemAudioInfo)
    fun release()
    fun pause()
    fun play()
}