package m.a.poem.ui.poem.model

import kotlinx.collections.immutable.ImmutableList
import m.a.poem.ui.book.model.BookItemUiModel
import m.a.poem.ui.book.model.PoemItemUiModel
import m.a.poem.ui.shared.model.PoetUiModel

data class PoemUiModel(
    val verses: ImmutableList<PoemVerseUiModel>,
    val recitations: ImmutableList<PoemRecitationUiModel>,
    val next: PoemItemUiModel?,
    val previous: PoemItemUiModel?,
    val poetUiModel: PoetUiModel,
    val bookUiModel: BookItemUiModel
)