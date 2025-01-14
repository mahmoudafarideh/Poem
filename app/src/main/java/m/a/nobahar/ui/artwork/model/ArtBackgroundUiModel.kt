package m.a.nobahar.ui.artwork.model

import androidx.annotation.DrawableRes

data class ArtBackgroundUiModel(
    @DrawableRes val image: Int,
    val selected: Boolean,
)