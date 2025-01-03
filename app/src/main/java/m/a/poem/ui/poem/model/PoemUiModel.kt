package m.a.poem.ui.poem.model

import kotlinx.collections.immutable.ImmutableList
import m.a.poem.ui.book.model.SubPoem

data class PoemUiModel(
    val verses: ImmutableList<PoemVerseUiModel>,
    val recitations: ImmutableList<PoemRecitationUiModel>,
    val next: SubPoem?,
    val previous: SubPoem?,
)