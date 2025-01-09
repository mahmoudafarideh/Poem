package m.a.poem.api.repository

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import m.a.poem.api.helper.AudioSyncHelper
import m.a.poem.domain.model.MediaPlayerState
import m.a.poem.domain.model.PoemAudioInfo
import m.a.poem.domain.repository.MediaPlayerRepository
import javax.inject.Inject
import javax.inject.Singleton

@kotlin.OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class MediaPlayerRepositoryImp @Inject constructor(
    private val exoPlayer: ExoPlayer,
    private val audioSyncHelper: AudioSyncHelper
) : MediaPlayerRepository {

    private var poemAudioInfo: PoemAudioInfo? = null
    private var audioSync: List<Pair<Int, Int>> = emptyList()
    override var state: MutableStateFlow<MediaPlayerState?> = MutableStateFlow(null)

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(job + Dispatchers.IO)

    init {
        observePlayerProgressChange()
        observeExoPlayerChanges()
    }

    private fun observeExoPlayerChanges() {
        exoPlayer.addListener(object : Player.Listener {

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                state.update { MediaPlayerState.LoadingFailed }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                if (isPlaying) {
                    poemAudioInfo?.let { recitation ->
                        state.update {
                            MediaPlayerState.Playing(
                                recitation.poemExcerpt,
                                recitation.recitation.artistName,
                                recitation.recitation.id,
                                audioSync.firstOrNull { (_, time) ->
                                    time > exoPlayer.currentPosition
                                }?.first
                            )
                        }
                    }
                } else {
                    poemAudioInfo?.let { recitation ->
                        state.update {
                            MediaPlayerState.Paused(
                                recitation.poemExcerpt,
                                recitation.recitation.artistName,
                                recitation.recitation.id,
                            )
                        }
                    }
                }
            }

            override fun onIsLoadingChanged(isLoading: Boolean) {
                super.onIsLoadingChanged(isLoading)
                if (isLoading && !exoPlayer.isPlaying) {
                    poemAudioInfo?.let { recitation ->
                        state.update {
                            MediaPlayerState.Loading(
                                recitation.poemExcerpt,
                                recitation.recitation.artistName,
                                recitation.recitation.id,
                            )
                        }
                    }
                }
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == Player.STATE_ENDED) {
                    poemAudioInfo?.let { recitation ->
                        state.update {
                            MediaPlayerState.Ended(
                                recitation.poemExcerpt,
                                recitation.recitation.artistName,
                                recitation.recitation.id,
                            )
                        }
                        poemAudioInfo = null
                    }
                }
            }

        })
    }

    private fun observePlayerProgressChange() {
        coroutineScope.launch(Dispatchers.Main) {
            state.flatMapLatest {
                flow {
                    while (true) {
                        poemAudioInfo?.let { recitation ->
                            emit(
                                MediaPlayerState.Playing(
                                    recitation.poemExcerpt,
                                    recitation.recitation.artistName,
                                    recitation.recitation.id,
                                    audioSync.lastOrNull { (_, time) ->
                                        time <= exoPlayer.currentPosition
                                    }?.first
                                )
                            )
                        }
                        delay(100)
                    }
                }
            }.collect {
                state.update { it }
            }
        }
    }

    override fun play(poemAudioInfo: PoemAudioInfo) {
        if (this.poemAudioInfo == poemAudioInfo) {
            if (!exoPlayer.isPlaying) {
                exoPlayer.play()
            }
            return
        }
        this@MediaPlayerRepositoryImp.poemAudioInfo = poemAudioInfo
        exoPlayer.apply {
            resetMusicPlayer()
            val mediaItem = MediaItem.fromUri(poemAudioInfo.recitation.mp3Url)
            setMediaItem(mediaItem)
            prepare()
        }
        coroutineScope.launch {
            poemAudioInfo.recitation.syncUrl?.let {
                state.update {
                    MediaPlayerState.Loading(
                        poemAudioInfo.poemExcerpt,
                        poemAudioInfo.recitation.artistName,
                        poemAudioInfo.recitation.id,
                    )
                }
                runCatching {
                    audioSyncHelper.getAudioSync(it)
                }.onFailure {
                    this@MediaPlayerRepositoryImp.poemAudioInfo = null
                    state.update { MediaPlayerState.LoadingFailed }
                }.onSuccess {
                    audioSync = it
                    withContext(Dispatchers.Main) {
                        exoPlayer.play()
                    }
                }
            } ?: run {
                withContext(Dispatchers.Main) {
                    exoPlayer.play()
                }
            }
        }

    }

    private fun ExoPlayer.resetMusicPlayer() {
        kotlin.runCatching { stop() }
    }

    @OptIn(UnstableApi::class)
    override fun release() {
        poemAudioInfo = null
        audioSync = emptyList()
        kotlin.runCatching {
            exoPlayer.resetMusicPlayer()
        }
        state.update { null }
    }

    override fun pause() {
        exoPlayer.pause()
    }

    override fun play() {
        poemAudioInfo?.let {
            exoPlayer.play()
        }
    }
}