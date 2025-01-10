package m.a.poem.ui.omen.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.Loading
import m.a.poem.ui.omen.component.ErrorMessageSnack
import m.a.poem.ui.omen.component.OmenHorizontalContent
import m.a.poem.ui.omen.component.OmenVerticalContent
import m.a.poem.ui.omen.model.OmenUiModel
import m.a.poem.ui.shared.ui.LocalWindowSize
import m.a.poem.ui.shared.ui.SabaPreview
import m.a.poem.ui.theme.PoemThemePreview

@Composable
fun OmenScreen(
    state: LoadableData<OmenUiModel>,
    onErrorDismiss: () -> Unit,
    onRetryClick: () -> Unit,
    onOmenClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowSize = LocalWindowSize.current

    var shouldShowMessageBar by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100)
        shouldShowMessageBar = true
    }

    ErrorMessageSnack(state, onRetryClick, onErrorDismiss)

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact) {
            OmenVerticalContent(
                shouldShowMessageBar,
                state,
                onOmenClick
            )
        } else {
            OmenHorizontalContent(
                shouldShowMessageBar,
                state,
                onOmenClick
            )
        }
    }


}

@SabaPreview
@Composable
fun OmenScreenPreview() {
    PoemThemePreview {
        OmenScreen(
            state = Loading,
            modifier = Modifier.fillMaxSize(),
            onErrorDismiss = {},
            onRetryClick = {},
            onOmenClick = {},
        )
    }
}