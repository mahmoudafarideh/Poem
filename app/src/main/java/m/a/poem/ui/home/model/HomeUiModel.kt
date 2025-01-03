package m.a.poem.ui.home.model

import kotlinx.collections.immutable.ImmutableList
import m.a.poem.ui.shared.model.PoetUiModel

data class HomeUiModel(
    val popularPoets: ImmutableList<PoetUiModel>,
    val labels: ImmutableList<CenturyUiModel>,
    val poets: ImmutableList<PoetUiModel>,
)