package m.a.poem.ui.book.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toImmutableList
import m.a.poem.domain.model.BookItem
import m.a.poem.domain.repository.PoetRepository
import m.a.poem.ui.book.model.BookScreenUiModel
import m.a.poem.ui.book.model.SubBook
import m.a.poem.ui.book.model.SubPoem
import m.a.poem.ui.shared.BaseViewModel
import m.a.poem.ui.shared.model.PoetUiModel

class BookViewModel @AssistedInject constructor(
    @Assisted private val poetUiModel: PoetUiModel,
    @Assisted private val bookId: Long,
    private val poetRepository: PoetRepository
) : BaseViewModel<BookScreenUiModel>(BookScreenUiModel(poetUiModel)) {

    init {
        getBookItems()
    }

    private fun getBookItems() {
        executeLoadable(
            state.value.items,
            action = {
                val items = poetRepository.getBook(bookId).items
                items.map {
                    when (it) {
                        is BookItem.Book -> SubBook(it.label, it.id)
                        is BookItem.Poem -> SubPoem(it.label, it.excerpt, it.id)
                    }
                }.toImmutableList()
            },
            data = {
                Log.d("SXO", "getBookItems $it")
                updateState { copy(items = it) }
            }
        )
    }

    fun retryClicked() {
        getBookItems()
    }


    @AssistedFactory
    interface Factory {
        fun create(poetUiModel: PoetUiModel, bookId: Long): BookViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            poetUiModel: PoetUiModel,
            bookId: Long,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(poetUiModel, bookId) as T
            }
        }
    }
}