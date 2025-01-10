package m.a.poem.ui.artwork.model

import androidx.annotation.StringRes
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import m.a.poem.R

data class ArtFontSizeUiModel(
    val size: Size,
    val selected: Boolean,
) {
    enum class Size(
        val verseSize: TextUnit,
        val poetSize: TextUnit,
        @StringRes val label: Int,
    ) {
        ExtraSmall(12.sp, 10.sp, R.string.extra_small_font_size_label),
        Small(14.sp, 12.sp, R.string.small_font_size_label),
        Medium(18.sp, 14.sp, R.string.medium_font_size_label),
        Large(22.sp, 16.sp, R.string.large_font_size_label),
        ExtraLarge(26.sp, 18.sp, R.string.extra_large_font_size_label),
    }
}