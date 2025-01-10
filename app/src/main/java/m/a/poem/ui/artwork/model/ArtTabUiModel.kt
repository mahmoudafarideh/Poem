package m.a.poem.ui.artwork.model

import androidx.annotation.StringRes
import m.a.nobahar.R

data class ArtTabUiModel(
    val tab: Tab,
    val selected: Boolean,
) {
    enum class Tab(@StringRes val title: Int) {
        Font(R.string.font_tab_label),
        Background(R.string.background_tab_label),
        Color(R.string.color_tab_label),
    }
}