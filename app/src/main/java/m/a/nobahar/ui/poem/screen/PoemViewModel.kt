package m.a.nobahar.ui.poem.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import m.a.nobahar.domain.model.Loaded
import m.a.nobahar.domain.model.MediaPlayerState
import m.a.nobahar.domain.model.PoemAudioInfo
import m.a.nobahar.domain.model.PoemInfo
import m.a.nobahar.domain.model.PoemVerse
import m.a.nobahar.domain.repository.MediaPlayerRepository
import m.a.nobahar.domain.repository.PoemRepository
import m.a.nobahar.ui.book.model.toBookItemUiModel
import m.a.nobahar.ui.book.model.toPoemItemUiModel
import m.a.nobahar.ui.poem.model.PoemRecitationUiModel
import m.a.nobahar.ui.poem.model.PoemScreenUiModel
import m.a.nobahar.ui.poem.model.PoemUiModel
import m.a.nobahar.ui.poem.model.PoemVerseUiModel
import m.a.nobahar.ui.poem.model.toPoemRecitationUiModel
import m.a.nobahar.ui.poem.model.toPoemVerseUiModel
import m.a.nobahar.ui.shared.BaseViewModel
import m.a.nobahar.ui.toPoetUiModel

class PoemViewModel @AssistedInject constructor(
    @Assisted private val poemId: Long,
    private val poemRepository: PoemRepository,
    private val mediaPlayerRepository: MediaPlayerRepository,
) : BaseViewModel<PoemScreenUiModel>(PoemScreenUiModel()) {

    init {
        getPoem()
    }

    fun retryClicked() {
        getPoem()
    }

    fun verseClicked(index: Int) {
        val poem = state.value.poem.data ?: return
        updateState {
            copy(
                poem = Loaded(
                    poem.copy(
                        verses = poem.verses.mapIndexed { verseIndex, verse ->
                            when (index) {
                                verseIndex -> verse.toggleSelection(isSelected = !verse.isSelected)
                                else -> verse
                            }
                        }.toImmutableList()
                    )
                )
            )
        }
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
        verses = verses.zipped().let {
            var iteratedVerses = 0
            it.mapIndexedNotNull { index, poemVerse ->
                poemVerse.toPoemVerseUiModel(index, iteratedVerses).also {
                    iteratedVerses += poemVerse.size
                }
            }.toImmutableList()
        },
        next = nextPoem?.toPoemItemUiModel(),
        previous = previousPoem?.toPoemItemUiModel(),
        recitations = recitations.map {
            it.toPoemRecitationUiModel()
        }.toImmutableList(),
        poetUiModel = poet.toPoetUiModel(),
        bookUiModel = book.toBookItemUiModel(),
        label = label
    )

    private fun List<PoemVerse>.zipped() = buildList {
        val coupleVerses: MutableList<PoemVerse> = mutableListOf()
        var previousIndex: Int = this@zipped.firstOrNull()?.couple ?: -1
        this@zipped.forEach {
            when {
                previousIndex != it.couple -> {
                    if (coupleVerses.isNotEmpty()) {
                        add(coupleVerses.toList())
                    }
                    coupleVerses.clear()
                    coupleVerses.add(it)
                    previousIndex = it.couple
                }

                else -> {
                    coupleVerses += it
                }
            }
        }
        if (coupleVerses.isNotEmpty()) {
            add(coupleVerses.toList())
        }
    }

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
                        verses = data.verses.map { verse ->
                            updateHighlightedVerse(verse, shouldHighlight, verseIndex)
                        }.toImmutableList(),
                    )
                )
            )
        }
    }

    private fun updateHighlightedVerse(
        verse: PoemVerseUiModel,
        shouldHighlight: Boolean,
        verseIndex: Int
    ): PoemVerseUiModel = verse.toggleHighlight(verseIndex, shouldHighlight)

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
                data.poetUiModel.toPoet(),
                PoemAudioInfo.Poem(
                    data.verses.first().first.text,
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

    fun releaseVerses() {
        val poem = state.value.poem.data ?: return
        updateState {
            copy(
                poem = Loaded(
                    poem.copy(
                        verses = poem.verses.map { verse ->
                            verse.toggleSelection(isSelected = false)
                        }.toImmutableList()
                    )
                )
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(poemId: Long): PoemViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            poemId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(poemId) as T
            }
        }
    }
}