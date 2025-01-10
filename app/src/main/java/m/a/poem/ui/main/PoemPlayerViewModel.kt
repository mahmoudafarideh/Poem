package m.a.poem.ui.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import m.a.poem.domain.model.PoemAudioInfo
import m.a.poem.domain.repository.MediaPlayerRepository
import m.a.poem.ui.main.model.MediaPlayerUiModel
import m.a.poem.ui.main.model.toMediaPlayerUiModel
import m.a.poem.ui.shared.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class PoemPlayerViewModel @Inject constructor(
    private val mediaPlayerRepository: MediaPlayerRepository
) : BaseViewModel<MediaPlayerUiModel?>(null) {

    private var _poemAudio: PoemAudioInfo? = null
    val poemAudio: PoemAudioInfo? get() = _poemAudio

    init {
        viewModelScope.launch {
            mediaPlayerRepository.state.collect {
                _poemAudio = it?.poemAudioInfo
                updateState { it?.toMediaPlayerUiModel() }
            }
        }
    }

    fun closeClicked() {
        mediaPlayerRepository.release()
    }

    fun playClicked() {
        mediaPlayerRepository.play()
    }

    fun pauseClicked() {
        mediaPlayerRepository.pause()
    }

}