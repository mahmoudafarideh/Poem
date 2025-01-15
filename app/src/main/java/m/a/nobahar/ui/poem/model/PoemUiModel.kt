package m.a.nobahar.ui.poem.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import m.a.nobahar.ui.book.model.BookItemUiModel
import m.a.nobahar.ui.book.model.PoemItemUiModel
import m.a.nobahar.ui.shared.model.PoetUiModel

data class PoemUiModel(
    val verses: ImmutableList<PoemVerseUiModel>,
    val recitations: ImmutableList<PoemRecitationUiModel>,
    val next: PoemItemUiModel?,
    val previous: PoemItemUiModel?,
    val poetUiModel: PoetUiModel,
    val bookUiModel: BookItemUiModel,
    val label: String
) {
    val selectedVerses = verses.filter { it.isSelected }.toImmutableList()
    val anyVerseSelected = selectedVerses.isNotEmpty()
    val isOneVerseSelected = selectedVerses.count { it is PoemVerseUiModel.Double } == 1
    val selectedVerse: PoemVerseUiModel.Double? = selectedVerses.filterIsInstance<PoemVerseUiModel.Double>().firstOrNull()
}