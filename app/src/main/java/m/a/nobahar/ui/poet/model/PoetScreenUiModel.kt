package m.a.nobahar.ui.poet.model

import kotlinx.collections.immutable.ImmutableList
import m.a.nobahar.domain.model.LoadableData
import m.a.nobahar.domain.model.NotLoaded
import m.a.nobahar.ui.shared.model.PoetUiModel

data class PoetScreenUiModel(
    val poet: PoetUiModel,
    val poetInfo: LoadableData<PoetInfo> = NotLoaded,
    val randomPoem: LoadableData<Unit> = NotLoaded,
    val selectedTabsUiModel: PoetScreenTabsUiModel = PoetScreenTabsUiModel.Books,
) {
    data class PoetInfo(
        val poetBioUiModel: PoetBioUiModel,
        val poetBooks: ImmutableList<PoetBooksUiModel>,
    )
}