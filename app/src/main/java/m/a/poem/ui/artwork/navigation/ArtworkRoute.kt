package m.a.poem.ui.artwork.navigation

import m.a.compilot.common.RouteNavigation
import m.a.poem.domain.model.PoemVerse

@RouteNavigation
data class ArtworkRoute(
    val first: PoemVerse,
    val second: PoemVerse,
    val poetName: String,
    val poemBook: String
) {
    companion object
}