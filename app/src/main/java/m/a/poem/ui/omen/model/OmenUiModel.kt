package m.a.poem.ui.omen.model

import m.a.poem.ui.shared.model.PoetUiModel

data class OmenUiModel(
    val poetUiModel: PoetUiModel,
    val poemId: Long
)