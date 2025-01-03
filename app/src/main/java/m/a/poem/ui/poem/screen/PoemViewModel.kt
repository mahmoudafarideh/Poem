package m.a.poem.ui.poem.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import m.a.poem.domain.model.Loaded
import m.a.poem.domain.model.PoemInfo
import m.a.poem.domain.repository.MediaPlayerRepository
import m.a.poem.domain.repository.PoetRepository
import m.a.poem.ui.book.model.SubPoem
import m.a.poem.ui.poem.model.MediaPlayerState
import m.a.poem.ui.poem.model.PoemRecitationUiModel
import m.a.poem.ui.poem.model.PoemScreenUiModel
import m.a.poem.ui.poem.model.PoemUiModel
import m.a.poem.ui.poem.model.PoemVerseUiModel
import m.a.poem.ui.shared.BaseViewModel
import m.a.poem.ui.shared.model.PoetUiModel

class PoemViewModel @AssistedInject constructor(
    @Assisted private val poetUiModel: PoetUiModel,
    @Assisted private val poemId: Long,
    private val poetRepository: PoetRepository,
    private val mediaPlayerRepository: MediaPlayerRepository,
) : BaseViewModel<PoemScreenUiModel>(PoemScreenUiModel(poetUiModel)) {

    private var musicPlayerJob: Job? = null

    init {
        getPoem()
    }

    fun retryClicked() {
        getPoem()
    }

    private fun getPoem() {
        executeLoadable(
            currentValue = state.value.poem,
            action = {
                val poem = poetRepository.getPoem(poemId)
                poem.toPoemUiModel()
            },
            data = {
                updateState { copy(poem = it) }
            }
        )
    }

    private fun PoemInfo.toPoemUiModel() = PoemUiModel(
        verses = verses.mapIndexed { index, poemVerse ->
            PoemVerseUiModel(
                text = poemVerse.text,
                id = poemVerse.id,
                position = if (index % 2 == 1) PoemVerseUiModel.VersePosition.End else PoemVerseUiModel.VersePosition.Start
            )
        }.toImmutableList(),
        next = nextPoem?.let {
            SubPoem(
                label = it.label,
                excerpt = it.excerpt,
                id = it.id
            )
        },
        previous = previousPoem?.let {
            SubPoem(
                label = it.label,
                excerpt = it.excerpt,
                id = it.id
            )
        },
        recitations = recitations.map {
            PoemRecitationUiModel(
                artist = it.artistName,
                mp3Url = it.mp3Url,
                id = it.id,
                state = PoemRecitationUiModel.State.None
            )
        }.toImmutableList()
    )

    private fun updateRecitationState(recitationId: Long, newState: PoemRecitationUiModel.State) {
        val data = state.value.poem.data ?: return
        updateState {
            copy(
                poem = Loaded(
                    data.copy(
                        recitations = data.recitations.map {
                            when (it.id) {
                                recitationId -> it.copy(state = newState)
                                else -> it.copy(state = PoemRecitationUiModel.State.None)
                            }
                        }.toImmutableList()
                    )
                )
            )
        }
    }

    fun recitationClicked(recitationId: Long) {
        val recitation = state.value.poem.data?.recitations?.firstOrNull {
            it.id == recitationId
        } ?: return

        if (recitation.state is PoemRecitationUiModel.State.Playing) {
            updateRecitationState(recitationId, PoemRecitationUiModel.State.Paused)
            mediaPlayerRepository.pause()
            return
        }

        if (recitation.state == PoemRecitationUiModel.State.Paused) {
            mediaPlayerRepository.play(recitation.mp3Url)
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            musicPlayerJob?.cancelAndJoin()
            musicPlayerJob = launch(Dispatchers.IO) {
                this.launch {
                    mediaPlayerRepository.playingProgress().collect {
                        updateRecitationState(
                            recitationId,
                            PoemRecitationUiModel.State.Playing(it.first, it.second)
                        )
                    }
                }
                this.launch {
                    mediaPlayerRepository.musicPlayingStarted().collect {
                        if (it != recitation.mp3Url) {
                            updateRecitationState(recitationId, PoemRecitationUiModel.State.None)
                        }
                    }
                }
                this.launch {
                    mediaPlayerRepository.playbackState().collect {
                        when (it) {
                            MediaPlayerState.Stopped -> {
                                updateRecitationState(
                                    recitationId,
                                    PoemRecitationUiModel.State.None
                                )
                            }

                            else -> {}
                        }
                    }
                }
            }
        }

        updateRecitationState(recitationId, PoemRecitationUiModel.State.Loading)
        mediaPlayerRepository.play(recitation.mp3Url)
    }


    override fun onCleared() {
        mediaPlayerRepository.release()
        super.onCleared()

    }

    @AssistedFactory
    interface Factory {
        fun create(poetUiModel: PoetUiModel, bookId: Long): PoemViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            poetUiModel: PoetUiModel,
            bookId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(poetUiModel, bookId) as T
            }
        }
    }
}