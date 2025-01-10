package m.a.poem.ui.search.model

import m.a.poem.ui.book.model.BookItemUiModel
import m.a.poem.ui.book.model.PoemItemUiModel
import m.a.poem.ui.shared.model.PoetUiModel

data class SearchResultUiModel(
    val poemUiModel: PoemItemUiModel,
    val poetUiModel: PoetUiModel,
    val bookUiModel: BookItemUiModel
)