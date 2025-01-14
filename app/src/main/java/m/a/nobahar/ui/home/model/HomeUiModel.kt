package m.a.nobahar.ui.home.model

import kotlinx.collections.immutable.ImmutableList
import m.a.nobahar.ui.shared.model.PoetUiModel

data class HomeUiModel(
    val popularPoets: ImmutableList<PoetUiModel>,
    val labels: ImmutableList<CenturyUiModel>,
    val poets: ImmutableList<PoetUiModel>,
    val communication: HomeCommunicationUiModel? = null
)