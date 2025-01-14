package m.a.nobahar.ui.book.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toImmutableList
import m.a.nobahar.domain.model.BookItem
import m.a.nobahar.domain.repository.BookRepository
import m.a.nobahar.ui.book.model.BookScreenUiModel
import m.a.nobahar.ui.book.model.BookItemUiModel
import m.a.nobahar.ui.book.model.PoemItemUiModel
import m.a.nobahar.ui.shared.BaseViewModel
import m.a.nobahar.ui.shared.model.PoetUiModel

class BookViewModel @AssistedInject constructor(
    @Assisted private val poetUiModel: PoetUiModel,
    @Assisted private val bookId: Long,
    private val bookRepository: BookRepository
) : BaseViewModel<BookScreenUiModel>(BookScreenUiModel(poetUiModel)) {

    init {
        getBookItems()
    }

    private fun getBookItems() {
        executeLoadable(
            state.value.items,
            action = {
                val items = bookRepository.getBook(bookId).items
                items.map {
                    when (it) {
                        is BookItem.Book -> BookItemUiModel(it.label, it.id)
                        is BookItem.Poem -> PoemItemUiModel(it.label, it.excerpt, it.id)
                    }
                }.toImmutableList()
            },
            data = {
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