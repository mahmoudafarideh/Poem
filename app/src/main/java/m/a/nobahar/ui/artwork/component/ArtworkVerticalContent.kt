package m.a.nobahar.ui.artwork.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import dev.shreyaspatil.capturable.controller.CaptureController
import m.a.nobahar.ui.artwork.model.ArtFontUiModel
import m.a.nobahar.ui.artwork.model.ArtSavingState
import m.a.nobahar.ui.artwork.model.ArtScreenUiModel
import m.a.nobahar.ui.artwork.model.ArtTabUiModel


@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun ArtworkVerticalContent(
    state: ArtScreenUiModel,
    onTabClick: (ArtTabUiModel.Tab) -> Unit,
    onFontClick: (ArtFontUiModel.Font) -> Unit,
    onFontSizeChange: (Int) -> Unit,
    onColorClick: (Color) -> Unit,
    onBackgroundClick: (Int) -> Unit,
    scaffoldState: BottomSheetScaffoldState,
    firstVerse: String,
    secondVerse: String,
    poetName: String,
    captureController: CaptureController,
    onMatnnegarClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomSheetScaffold(
        sheetPeekHeight = when (state.savingState) {
            ArtSavingState.Saved -> 1.dp
            else -> 240.dp
        },
        sheetContent = {
            ArtworkTools(
                state = state,
                onTabClick = onTabClick,
                onFontClick = onFontClick,
                onFontSizeChange = onFontSizeChange,
                onColorClick = onColorClick,
                onBackgroundClick = onBackgroundClick,
                modifier = Modifier.graphicsLayer {
                    alpha = if (state.savingState != ArtSavingState.None) 0.5f else 1f
                }
            )
        },
        scaffoldState = scaffoldState,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SavingStateMessages(state)
            ArtworkBox(
                firstVerse = firstVerse,
                secondVerse = secondVerse,
                poetName = poetName,
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(16.dp),
                state = state,
                captureController = captureController
            )
            MatnnegarRow(
                Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable {
                        onMatnnegarClick()
                    }
                    .padding(4.dp)
            )
        }
    }
}