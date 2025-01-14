package m.a.nobahar.ui.search.model

import m.a.nobahar.ui.book.model.BookItemUiModel
import m.a.nobahar.ui.book.model.PoemItemUiModel
import m.a.nobahar.ui.shared.model.PoetUiModel

data class SearchResultUiModel(
    val poemUiModel: PoemItemUiModel,
    val poetUiModel: PoetUiModel,
    val bookUiModel: BookItemUiModel
)