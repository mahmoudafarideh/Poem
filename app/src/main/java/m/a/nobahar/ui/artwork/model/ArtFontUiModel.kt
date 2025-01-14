package m.a.nobahar.ui.artwork.model

import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.compose.ui.text.font.FontFamily
import m.a.nobahar.R

data class ArtFontUiModel(
    val font: Font,
    val selected: Boolean,
) {
    enum class Font(
        @FontRes private val font: Int,
        @StringRes val label: Int,
    ) {
        Nastaliq(R.font.iran_nastaliq, R.string.nastaliq_font_label),
        Vazir(R.font.vazir_regular, R.string.vazir_font_label),
        Ordibehesht(R.font.ordibehesht, R.string.ordibehesht_font_label);

        val fontFamily = FontFamily(androidx.compose.ui.text.font.Font(font))
    }
}