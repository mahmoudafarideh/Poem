package m.a.nobahar.ui.artwork.model

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class ArtFontSizeUiModel(
    val size: Size,
    val selected: Boolean,
) {
    enum class Size(
        val verseSize: TextUnit,
        val poetSize: TextUnit,
        val progress: Int,
    ) {
        Tiny(12.sp, 10.sp, 1),
        ExtraSmall(14.sp, 12.sp, 2),
        Small(18.sp, 14.sp, 3),
        Medium(20.sp, 16.sp, 4),
        Large(24.sp, 18.sp, 5),
        ExtraLarge(28.sp, 20.sp, 6),
        Big(30.sp, 22.sp, 7),
        ExtraBig(32.sp, 24.sp, 8),
        Huge(34.sp, 26.sp, 9),
    }
}