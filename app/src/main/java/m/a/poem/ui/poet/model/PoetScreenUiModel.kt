package m.a.poem.ui.poet.model

import kotlinx.collections.immutable.ImmutableList
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.NotLoaded
import m.a.poem.ui.shared.model.PoetUiModel

data class PoetScreenUiModel(
    val poet: PoetUiModel,
    val poetInfo: LoadableData<PoetInfo> = NotLoaded,
    val selectedTabsUiModel: PoetScreenTabsUiModel = PoetScreenTabsUiModel.Books,
) {
    data class PoetInfo(
        val poetBioUiModel: PoetBioUiModel,
        val poetBooks: ImmutableList<PoetBooksUiModel>,
    )
}