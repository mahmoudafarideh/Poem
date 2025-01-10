package m.a.poem.ui.poem.model

import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.NotLoaded

data class PoemScreenUiModel(
    val poem: LoadableData<PoemUiModel> = NotLoaded
)