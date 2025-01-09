package m.a.poem.ui.search.model

import m.a.poem.ui.book.model.SubBook
import m.a.poem.ui.book.model.SubPoem
import m.a.poem.ui.shared.model.PoetUiModel

data class SearchResultUiModel(
    val poemUiModel: SubPoem,
    val poetUiModel: PoetUiModel,
    val bookUiModel: SubBook
)