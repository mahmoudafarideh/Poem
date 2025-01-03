package m.a.poem.ui.poem.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toImmutableList
import m.a.poem.domain.model.PoemInfo
import m.a.poem.domain.repository.PoetRepository
import m.a.poem.ui.book.model.SubPoem
import m.a.poem.ui.poem.model.PoemScreenUiModel
import m.a.poem.ui.poem.model.PoemUiModel
import m.a.poem.ui.poem.model.PoemVerseUiModel
import m.a.poem.ui.shared.BaseViewModel
import m.a.poem.ui.shared.model.PoetUiModel

class PoemViewModel @AssistedInject constructor(
    @Assisted private val poetUiModel: PoetUiModel,
    @Assisted private val poemId: Long,
    private val poetRepository: PoetRepository
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
        }
    )

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