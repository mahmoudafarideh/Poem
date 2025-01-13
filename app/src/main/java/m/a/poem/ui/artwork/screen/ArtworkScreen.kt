package m.a.poem.ui.artwork.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.shreyaspatil.capturable.controller.CaptureController
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import m.a.poem.ui.artwork.component.ArtworkAppBar
import m.a.poem.ui.artwork.component.ArtworkHorizontalContent
import m.a.poem.ui.artwork.component.ArtworkVerticalContent
import m.a.poem.ui.artwork.model.ArtFontUiModel
import m.a.poem.ui.artwork.model.ArtSavingState
import m.a.poem.ui.artwork.model.ArtScreenUiModel
import m.a.poem.ui.artwork.model.ArtTabUiModel
import m.a.poem.ui.shared.ui.LocalWindowSize
import m.a.poem.ui.shared.ui.SabaPreview
import m.a.poem.ui.theme.PoemThemePreview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeApi::class)
@Composable
fun ArtworkScreen(
    firstVerse: String,
    secondVerse: String,
    poetName: String,
    bookName: String,
    state: ArtScreenUiModel,
    onTabClick: (ArtTabUiModel.Tab) -> Unit,
    onFontClick: (ArtFontUiModel.Font) -> Unit,
    onFontSizeChange: (Int) -> Unit,
    onColorClick: (Color) -> Unit,
    onSaveButtonClick: () -> Unit,
    onMatnnegarClick: () -> Unit,
    onBackgroundClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    captureController: CaptureController
) {
    val windowSize = LocalWindowSize.current
    val scaffoldState = rememberBottomSheetScaffoldState()
    Scaffold(
        topBar = {
            if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact) {
                ArtworkAppBar(state, onSaveButtonClick)
            }
        },
        modifier = modifier
    ) { contentPadding ->
        when (windowSize.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                ArtworkVerticalContent(
                    state = state,
                    onTabClick = onTabClick,
                    onFontClick = onFontClick,
                    onFontSizeChange = onFontSizeChange,
                    onColorClick = onColorClick,
                    onBackgroundClick = onBackgroundClick,
                    scaffoldState = scaffoldState,
                    firstVerse = firstVerse,
                    secondVerse = secondVerse,
                    poetName = poetName,
                    bookName = bookName,
                    captureController = captureController,
                    modifier = Modifier.padding(contentPadding),
                    onMatnnegarClick = onMatnnegarClick
                )
            }

            WindowWidthSizeClass.Expanded -> {
                ArtworkHorizontalContent(
                    state = state,
                    onTabClick = onTabClick,
                    onFontClick = onFontClick,
                    onFontSizeChange = onFontSizeChange,
                    onColorClick = onColorClick,
                    onBackgroundClick = onBackgroundClick,
                    firstVerse = firstVerse,
                    secondVerse = secondVerse,
                    poetName = poetName,
                    bookName = bookName,
                    onSaveButtonClick = onSaveButtonClick,
                    captureController = captureController,
                    modifier = Modifier.padding(contentPadding),
                    onMatnnegarClick = onMatnnegarClick
                )
            }
        }
    }
}


@SabaPreview
@Composable
private fun ArtworkScreenPreview() {
    PoemThemePreview {
        ArtworkScreen(
            firstVerse = "اَلا یا اَیُّهَا السّاقی اَدِرْ کَأسَاً و ناوِلْها",
            secondVerse = "که عشق آسان نُمود اوّل ولی افتاد مشکل\u200Cها",
            poetName = "حافظ",
            bookName = "غزلیات",
            state = ArtScreenUiModel.default.copy(
                savingState = ArtSavingState.Failed
            ),
            onTabClick = {},
            onFontClick = {},
            onFontSizeChange = {},
            onColorClick = {},
            onBackgroundClick = {},
            onSaveButtonClick = {},
            onMatnnegarClick = {},
            captureController = rememberCaptureController()
        )
    }
}