package m.a.poem.ui.widget.model

import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.NotLoaded

data class WidgetUiModel(
    val poemVerse: LoadableData<WidgetPoemVerseUiModel> = NotLoaded
)