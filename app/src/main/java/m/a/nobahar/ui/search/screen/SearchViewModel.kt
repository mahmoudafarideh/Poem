package m.a.nobahar.ui.search.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import m.a.nobahar.domain.model.NotInitialLoaded
import m.a.nobahar.domain.model.PoemSearchFilter
import m.a.nobahar.domain.model.PoemSearchResult
import m.a.nobahar.domain.repository.SearchRepository
import m.a.nobahar.ui.book.model.BookItemUiModel
import m.a.nobahar.ui.book.model.PoemItemUiModel
import m.a.nobahar.ui.search.model.SearchBookUiModel
import m.a.nobahar.ui.search.model.SearchResultUiModel
import m.a.nobahar.ui.search.model.SearchScreenUiModel
import m.a.nobahar.ui.shared.BaseViewModel
import m.a.nobahar.ui.shared.model.PoetUiModel
import m.a.nobahar.ui.toPoetUiModel

class SearchViewModel @AssistedInject constructor(
    @Assisted private val poetUiModel: PoetUiModel?,
    @Assisted private val book: SearchBookUiModel?,
    private val searchRepository: SearchRepository
) : BaseViewModel<SearchScreenUiModel>(SearchScreenUiModel(poetUiModel, book)) {

    private val queryFlow = MutableStateFlow("")

    init {
        observeSearchQuery()
    }

    fun termChanged(term: String) {
        queryFlow.update { term }
        updateState { copy(term = term) }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            queryFlow.debounce(500).collectLatest {
                updateState {
                    copy(result = NotInitialLoaded(1, 20))
                }
                searchForPoems(it)
            }
        }
    }


    private suspend fun searchForPoems(query: String) {
        if (query.length < 2) return
        coroutineScope {
            executePaginateLoadable(
                currentValue = state.value.result,
                action = { page, limit ->
                    searchRepository.searchPoem(
                        PoemSearchFilter(
                            query = query,
                            page = page,
                            limit = limit,
                            poetId = poetUiModel?.id,
                            bookId = book?.id
                        )
                    ).map {
                        it.toSearchResultUiModel()
                    }
                },
                data = {
                    updateState {
                        copy(result = it)
                    }
                }
            )
        }
    }

    private fun PoemSearchResult.toSearchResultUiModel(): SearchResultUiModel = SearchResultUiModel(
        poemUiModel = PoemItemUiModel(
            id = poem.id,
            label = poem.label,
            excerpt = poem.excerpt,
        ),
        poetUiModel = poet.toPoetUiModel(),
        bookUiModel = BookItemUiModel(
            label = book.label,
            id = book.id
        )
    )

    fun retryClicked() {
        viewModelScope.launch {
            searchForPoems(queryFlow.value)
        }
    }

    fun listReachedEnd() {
        viewModelScope.launch {
            searchForPoems(queryFlow.value)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(poetUiModel: PoetUiModel?, book: SearchBookUiModel?): SearchViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            poetUiModel: PoetUiModel?,
            book: SearchBookUiModel?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(poetUiModel, book) as T
            }
        }
    }
}