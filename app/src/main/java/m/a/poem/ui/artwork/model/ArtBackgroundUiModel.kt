package m.a.poem.ui.artwork.model

import androidx.annotation.DrawableRes

data class ArtBackgroundUiModel(
    @DrawableRes val image: Int,
    val selected: Boolean,
)