package m.a.poem.api.repository

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.STATE_BUFFERING
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import m.a.poem.domain.repository.MediaPlayerRepository
import m.a.poem.ui.poem.model.MediaPlayerState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaPlayerRepositoryImp @Inject constructor(
    private val exoPlayer: ExoPlayer
) : MediaPlayerRepository {

    private var url: String? = null

    override fun play(url: String) {
        if (this.url == url) {
            if (!exoPlayer.isPlaying) {
                exoPlayer.play()
            }
            return
        }
        this.url = url
        exoPlayer.apply {
            resetMusicPlayer()
            val mediaItem = MediaItem.fromUri(url)
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    private fun ExoPlayer.resetMusicPlayer() {
        kotlin.runCatching { stop() }
    }

    @kotlin.OptIn(DelicateCoroutinesApi::class)
    override fun playingProgress(): Flow<Pair<Long, Long>> = channelFlow {
        while (!isClosedForSend) {
            if (exoPlayer.isPlaying) {
                trySend(exoPlayer.currentPosition to exoPlayer.duration)
            }
            delay(1_000)
        }
        awaitClose {}
    }.flowOn(Dispatchers.Main)

    override fun musicPlayingStarted(): Flow<String?> = channelFlow {
        while (currentCoroutineContext().isActive) {
            withContext(Dispatchers.Main) {
                if (exoPlayer.isPlaying) {
                    send(url)
                }
            }
            delay(1_000)
        }
        awaitClose()
    }.distinctUntilChanged()

    override fun playbackState(): Flow<MediaPlayerState> = channelFlow {
        while (currentCoroutineContext().isActive) {
            withContext(Dispatchers.Main) {
                when (exoPlayer.playbackState) {
                    STATE_ENDED -> MediaPlayerState.Stopped
                    STATE_BUFFERING -> MediaPlayerState.Loading
                    else -> null
                }?.let {
                    send(it)
                }
            }
            delay(1_000)
        }
        awaitClose()
    }.distinctUntilChanged()

    @OptIn(UnstableApi::class)
    override fun release() {
        url = null
        kotlin.runCatching {
            exoPlayer.resetMusicPlayer()
        }
    }

    override fun pause() {
        exoPlayer.pause()
    }
}