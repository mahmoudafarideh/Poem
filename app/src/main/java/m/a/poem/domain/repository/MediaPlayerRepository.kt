package m.a.poem.domain.repository

import kotlinx.coroutines.flow.Flow
import m.a.poem.ui.poem.model.MediaPlayerState

interface MediaPlayerRepository {
    fun play(url: String)
    fun playingProgress(): Flow<Pair<Long, Long>>
    fun musicPlayingStarted(): Flow<String?>
    fun playbackState(): Flow<MediaPlayerState>
    fun release()
    fun pause()
}