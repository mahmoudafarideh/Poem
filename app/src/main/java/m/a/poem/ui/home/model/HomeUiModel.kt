package m.a.poem.ui.home.model

import kotlinx.collections.immutable.ImmutableList

data class HomeUiModel(
    val popularPoets: ImmutableList<PoetUiModel>,
    val labels: ImmutableList<CenturyUiModel>,
    val poets: ImmutableList<PoetUiModel>,
)