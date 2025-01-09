package m.a.poem.ui.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import m.a.poem.domain.model.MediaPlayerState
import m.a.poem.domain.repository.MediaPlayerRepository
import m.a.poem.ui.shared.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class PoemPlayerViewModel @Inject constructor(
    private val mediaPlayerRepository: MediaPlayerRepository
) : BaseViewModel<MediaPlayerState?>(null) {

    init {
        viewModelScope.launch {
            mediaPlayerRepository.state.collect {
                updateState { it }
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