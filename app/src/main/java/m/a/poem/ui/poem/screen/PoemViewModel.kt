package m.a.poem.ui.poem.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import m.a.poem.domain.model.Loaded
import m.a.poem.domain.model.MediaPlayerState
import m.a.poem.domain.model.PoemAudioInfo
import m.a.poem.domain.model.PoemInfo
import m.a.poem.domain.repository.MediaPlayerRepository
import m.a.poem.domain.repository.PoemRepository
import m.a.poem.ui.book.model.toBookItemUiModel
import m.a.poem.ui.book.model.toPoemItemUiModel
import m.a.poem.ui.poem.model.PoemRecitationUiModel
import m.a.poem.ui.poem.model.PoemScreenUiModel
import m.a.poem.ui.poem.model.PoemUiModel
import m.a.poem.ui.poem.model.toPoemRecitationUiModel
import m.a.poem.ui.poem.model.toPoemVerseUiModel
import m.a.poem.ui.shared.BaseViewModel
import m.a.poem.ui.shared.model.PoetUiModel
import m.a.poem.ui.toPoetUiModel

class PoemViewModel @AssistedInject constructor(
    @Assisted private val poetUiModel: PoetUiModel,
    @Assisted private val poemId: Long,
    private val poemRepository: PoemRepository,
    private val mediaPlayerRepository: MediaPlayerRepository,
) : BaseViewModel<PoemScreenUiModel>(PoemScreenUiModel(poetUiModel)) {

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
                val poem = poemRepository.getPoem(poemId)
                poem.toPoemUiModel()
            },
            data = {
                updateState { copy(poem = it) }
                it.data?.let { poem ->
                    observePoemAudioPlayer()
                }
            }
        )
    }

    private fun observePoemAudioPlayer() {
        viewModelScope.launch(Dispatchers.IO) {
            mediaPlayerRepository.state.collect { mediaState ->
                state.value.poem.data?.recitations?.firstOrNull {
                    it.id == mediaState?.poemAudioInfo?.recitation?.id || it.state != PoemRecitationUiModel.State.None
                }?.let {
                    updateRecitationState(mediaState, it.id)
                }
            }
        }
    }

    private fun PoemInfo.toPoemUiModel() = PoemUiModel(
        verses = verses.mapIndexed { index, poemVerse ->
            poemVerse.toPoemVerseUiModel(index)
        }.toImmutableList(),
        next = nextPoem?.toPoemItemUiModel(),
        previous = previousPoem?.toPoemItemUiModel(),
        recitations = recitations.map {
            it.toPoemRecitationUiModel()
        }.toImmutableList(),
        poetUiModel = poet.toPoetUiModel(),
        bookUiModel = book.toBookItemUiModel()
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

    private fun updateVerseHighlightState(shouldHighlight: Boolean, verseIndex: Int) {
        val data = state.value.poem.data ?: return
        updateState {
            copy(
                poem = Loaded(
                    data.copy(
                        verses = data.verses.mapIndexed { index, verse ->
                            verse.copy(isHighlighted = shouldHighlight && index <= verseIndex)
                        }.toImmutableList(),
                    )
                )
            )
        }
    }

    fun recitationClicked(recitationId: Long) {
        val data = state.value.poem.data ?: return
        val recitation = data.recitations.firstOrNull {
            it.id == recitationId
        } ?: return

        if (recitation.state is PoemRecitationUiModel.State.Playing) {
            updateRecitationState(recitationId, PoemRecitationUiModel.State.Paused)
            mediaPlayerRepository.pause()
            return
        }

        if (recitation.state == PoemRecitationUiModel.State.Paused) {
            mediaPlayerRepository.play()
            return
        }
        mediaPlayerRepository.play(
            PoemAudioInfo(
                recitation.toRecitation(),
                poetUiModel.toPoet(),
                PoemAudioInfo.Poem(
                    data.verses.first().text,
                    poemId
                )
            )
        )
    }

    private fun updateRecitationState(
        mediaPlayerState: MediaPlayerState?,
        recitationId: Long
    ) {
        when {
            mediaPlayerState?.poemAudioInfo?.recitation?.id != recitationId -> {
                updateVerseHighlightState(false, -1)
                updateRecitationState(recitationId, PoemRecitationUiModel.State.None)
            }

            else -> when (mediaPlayerState) {
                is MediaPlayerState.Ended -> PoemRecitationUiModel.State.None
                is MediaPlayerState.Loading -> PoemRecitationUiModel.State.Loading
                MediaPlayerState.LoadingFailed -> PoemRecitationUiModel.State.None
                is MediaPlayerState.Paused -> PoemRecitationUiModel.State.Paused
                is MediaPlayerState.Playing -> PoemRecitationUiModel.State.Playing
            }.let {
                if (mediaPlayerState is MediaPlayerState.Playing) {
                    updateVerseHighlightState(true, mediaPlayerState.playingVerseIndex ?: -1)
                } else if (mediaPlayerState !is MediaPlayerState.Paused) {
                    updateVerseHighlightState(false, -1)
                }
                updateRecitationState(recitationId, it)
            }
        }
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