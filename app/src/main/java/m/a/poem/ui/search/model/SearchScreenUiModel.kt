package m.a.poem.ui.search.model

import m.a.poem.domain.model.NotInitialLoaded
import m.a.poem.domain.model.PaginateLoadableData
import m.a.poem.ui.shared.model.PoetUiModel

data class SearchScreenUiModel(
    val poetUiModel: PoetUiModel?,
    val bookUiModel: SearchBookUiModel?,
    val result: PaginateLoadableData<SearchResultUiModel> = NotInitialLoaded(1, 10),
    val term: String = ""
) {
    val shouldShowSearchLimit = term.length < 2
}