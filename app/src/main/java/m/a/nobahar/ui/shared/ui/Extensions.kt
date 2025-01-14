package m.a.nobahar.ui.shared.ui

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

fun Modifier.scrollShadow(
    state: ScrollableState
) = composed {
    val isScrolled by remember {
        derivedStateOf { state.canScrollBackward }
    }
    this.then(
        when {
            isScrolled -> Modifier.shadow(
                4.dp,
                ambientColor = MaterialTheme.colorScheme.onBackground.copy(.6f),
                spotColor = MaterialTheme.colorScheme.onBackground.copy(.6f),
            )

            else -> Modifier
        }
    )
}