package m.a.poem.ui.artwork.model

import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import m.a.poem.R

data class ArtScreenUiModel(
    val tabs: ImmutableList<ArtTabUiModel>,
    val fonts: ImmutableList<ArtFontUiModel>,
    val backgrounds: ImmutableList<ArtBackgroundUiModel>,
    val colors: ImmutableList<ArtColorUiModel>,
    val fontSizes: ImmutableList<ArtFontSizeUiModel>,
    val savingState: ArtSavingState,
) {
    val selectedFont = fonts.first { it.selected }
    val selectedFontSize = fontSizes.first { it.selected }
    val selectedBackground = backgrounds.first { it.selected }
    val selectedColor = colors.first { it.selected }
    val selectedTab = tabs.first { it.selected }

    companion object {
        val default = ArtScreenUiModel(
            tabs = ArtTabUiModel.Tab.entries.mapIndexed { index, item ->
                ArtTabUiModel(item, index == 0)
            }.toImmutableList(),
            fonts = ArtFontUiModel.Font.entries.mapIndexed { index, item ->
                ArtFontUiModel(item, index == 0)
            }.toImmutableList(),
            backgrounds = persistentListOf(
                ArtBackgroundUiModel(R.drawable.bg_poem_1, true),
                ArtBackgroundUiModel(R.drawable.bg_poem_1, false),
            ),
            colors = persistentListOf(
                ArtColorUiModel(Color.White, true),
                ArtColorUiModel(Color.Black, false),
                ArtColorUiModel(Color.Yellow, false),
                ArtColorUiModel(Color.Red, false),
                ArtColorUiModel(Color.Green, false),
                ArtColorUiModel(Color.Cyan, false),
                ArtColorUiModel(Color.Magenta, false),
                ArtColorUiModel(Color.LightGray, false),
            ),
            fontSizes = ArtFontSizeUiModel.Size.entries.mapIndexed { index, item ->
                ArtFontSizeUiModel(item, index == 2)
            }.toImmutableList(),
            savingState = ArtSavingState.None
        )
    }
}