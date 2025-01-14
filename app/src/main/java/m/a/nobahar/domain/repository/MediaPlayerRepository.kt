package m.a.nobahar.domain.repository

import kotlinx.coroutines.flow.StateFlow
import m.a.nobahar.domain.model.MediaPlayerState
import m.a.nobahar.domain.model.PoemAudioInfo

interface MediaPlayerRepository {
    val state: StateFlow<MediaPlayerState?>
    fun play(poemAudioInfo: PoemAudioInfo)
    fun release()
    fun pause()
    fun play()
}