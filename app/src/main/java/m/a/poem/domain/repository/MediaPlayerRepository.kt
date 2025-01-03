package m.a.poem.domain.repository

import kotlinx.coroutines.flow.Flow

interface MediaPlayerRepository {
    fun play(url: String)
    fun playingProgress(): Flow<Pair<Long, Long>>
    fun musicPlayingStarted(): Flow<String?>
    fun release()
    fun pause()
}