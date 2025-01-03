package m.a.poem.ui.poem.model

import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.NotLoaded
import m.a.poem.ui.shared.model.PoetUiModel

data class PoemScreenUiModel(
    val poet: PoetUiModel,
    val poem: LoadableData<PoemUiModel> = NotLoaded
)