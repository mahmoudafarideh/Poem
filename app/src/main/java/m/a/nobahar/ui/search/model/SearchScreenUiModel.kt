package m.a.nobahar.ui.search.model

import m.a.nobahar.domain.model.NotInitialLoaded
import m.a.nobahar.domain.model.PaginateLoadableData
import m.a.nobahar.ui.shared.model.PoetUiModel

data class SearchScreenUiModel(
    val poetUiModel: PoetUiModel?,
    val bookUiModel: SearchBookUiModel?,
    val result: PaginateLoadableData<SearchResultUiModel> = NotInitialLoaded(1, 20),
    val term: String = ""
) {
    val shouldShowSearchLimit = term.length < 2
}