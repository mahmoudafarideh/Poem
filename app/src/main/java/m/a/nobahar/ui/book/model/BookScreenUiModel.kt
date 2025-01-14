package m.a.nobahar.ui.book.model

import kotlinx.collections.immutable.ImmutableList
import m.a.nobahar.domain.model.LoadableData
import m.a.nobahar.domain.model.NotLoaded
import m.a.nobahar.ui.shared.model.PoetUiModel

data class BookScreenUiModel(
    val poet: PoetUiModel,
    val items: LoadableData<ImmutableList<BookSubItemUiModel>> = NotLoaded
)