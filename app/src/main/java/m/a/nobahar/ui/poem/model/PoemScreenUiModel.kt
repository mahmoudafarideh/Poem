package m.a.nobahar.ui.poem.model

import m.a.nobahar.domain.model.LoadableData
import m.a.nobahar.domain.model.NotLoaded

data class PoemScreenUiModel(
    val poem: LoadableData<PoemUiModel> = NotLoaded
)