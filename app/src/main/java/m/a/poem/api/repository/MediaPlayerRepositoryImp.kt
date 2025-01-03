package m.a.poem.api.repository

import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import m.a.poem.domain.repository.MediaPlayerRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaPlayerRepositoryImp @Inject constructor(
    private val exoPlayer: ExoPlayer
) : MediaPlayerRepository {

    private var url: String? = null

    init {

    }

    override fun play(url: String) {
        if (this.url == url) {
            if (!exoPlayer.isPlaying) {
                exoPlayer.play()
            }
            return
        }
        this.url = url
        exoPlayer.apply {
            stop()
            val mediaItem = MediaItem.fromUri(url)
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    private fun ExoPlayer.resetMusicPlayer() {
        kotlin.runCatching { stop() }
    }

    override fun playingProgress(): Flow<Pair<Long, Long>> = channelFlow {
        while (currentCoroutineContext().isActive) {
            withContext(Dispatchers.Main) {
                if (exoPlayer.isPlaying) {
                    send(exoPlayer.currentPosition to exoPlayer.duration)
                }
            }
            delay(1_000)
        }
    }

    override fun musicPlayingStarted(): Flow<String?> = channelFlow {
        while (currentCoroutineContext().isActive) {
            withContext(Dispatchers.Main) {
                if (exoPlayer.isPlaying) {
                    send(url)
                }
            }
            delay(1_000)
        }
    }.distinctUntilChanged()

    @OptIn(UnstableApi::class)
    override fun release() {
        kotlin.runCatching {
            exoPlayer.resetMusicPlayer()
        }
        kotlin.runCatching {
            if (!exoPlayer.isReleased) {
                exoPlayer.release()
            }
        }
    }

    override fun pause() {
        exoPlayer.pause()
    }
}