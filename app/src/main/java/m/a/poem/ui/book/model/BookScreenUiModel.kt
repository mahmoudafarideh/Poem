package m.a.poem.ui.book.model

import kotlinx.collections.immutable.ImmutableList
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.NotLoaded
import m.a.poem.ui.shared.model.PoetUiModel

data class BookScreenUiModel(
    val poet: PoetUiModel,
    val items: LoadableData<ImmutableList<BookSubItemUiModel>> = NotLoaded
)