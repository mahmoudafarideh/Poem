package m.a.poem.ui.poem.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.poem.domain.model.Failed
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.Loaded
import m.a.poem.domain.model.Loading
import m.a.poem.domain.model.NotLoaded
import m.a.poem.ui.book.model.SubPoem
import m.a.poem.ui.poem.components.PoemDetailsShimmer
import m.a.poem.ui.poem.components.PoemVerses
import m.a.poem.ui.poem.components.RecitationsColumn
import m.a.poem.ui.poem.model.PoemRecitationUiModel
import m.a.poem.ui.poem.model.PoemUiModel
import m.a.poem.ui.poem.model.PoemVerseUiModel
import m.a.poem.ui.shared.components.FetchingDataFailed
import m.a.poem.ui.shared.components.PoetAppBar
import m.a.poem.ui.shared.model.PoetUiModel
import m.a.poem.ui.theme.PoemThemePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoemScreen(
    poetUiModel: PoetUiModel,
    poemUiModel: LoadableData<PoemUiModel>,
    onRetryClick: () -> Unit,
    onPoemClick: (Long) -> Unit,
    onRecitationClicked: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val navigation = LocalNavController.comPilotNavController
    Scaffold(
        topBar = {
            PoetAppBar(
                poetUiModel = poetUiModel,
                onBackClick = { navigation.safePopBackStack() },
                modifier = Modifier
            )
        },
        modifier = modifier
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (poemUiModel) {
                Failed -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    FetchingDataFailed(
                        onRetryClick = onRetryClick
                    )
                }

                is Loaded -> {
                    if (poemUiModel.data.recitations.isEmpty()) {
                        PoemVerses(poemUiModel, onPoemClick)
                    } else {
                        BottomSheetScaffold(
                            sheetPeekHeight = 124.dp,
                            sheetContent = {
                                RecitationsColumn(poemUiModel, onRecitationClicked)
                            }
                        ) {
                            PoemVerses(poemUiModel, onPoemClick, Modifier.padding(it))
                        }
                    }
                }

                Loading -> {
                    PoemDetailsShimmer()
                }

                NotLoaded -> {}
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PoemScreenPreview() {
    PoemThemePreview {
        PoemScreen(
            poetUiModel = PoetUiModel.fixture,
            modifier = Modifier,
            poemUiModel = Loaded(
                PoemUiModel(
                    verses = persistentListOf(
                        PoemVerseUiModel(
                            text = "ای رستخیز ناگهان وی رحمت بی\u200Cمنتها",
                            id = 1,
                            PoemVerseUiModel.VersePosition.Start
                        ),
                        PoemVerseUiModel(
                            text = "ای آتشی افروخته، در بیشه اندیشه\u200Cها",
                            id = 2,
                            PoemVerseUiModel.VersePosition.End
                        ),
                        PoemVerseUiModel(
                            text = "ای رستخیز ناگهان وی رحمت بی\u200Cمنتها",
                            id = 3,
                            PoemVerseUiModel.VersePosition.Start
                        ),
                        PoemVerseUiModel(
                            text = "ای آتشی افروخته، در بیشه اندیشه\u200Cها",
                            id = 4,
                            PoemVerseUiModel.VersePosition.End
                        ),
                    ),
                    next = SubPoem(
                        label = "غزل شماره ۵",
                        excerpt = "الا ای آهوی وحشی کجایی",
                        id = 12
                    ),
                    previous = SubPoem(
                        label = "غزل شماره ۶",
                        excerpt = "الا ای آهوی وحشی کجایی",
                        id = 12
                    ),
                    recitations = persistentListOf(
                        PoemRecitationUiModel(
                            id = 1,
                            artist = "محمود آفریده",
                            mp3Url = "folan.mp3",
                            state = PoemRecitationUiModel.State.Loading
                        ),
                        PoemRecitationUiModel(
                            id = 2,
                            artist = "ابوالفضل آفریده",
                            mp3Url = "folan.mp3",
                            state = PoemRecitationUiModel.State.Paused
                        )
                    )
                )
            ),
            onRetryClick = {},
            onPoemClick = {},
            onRecitationClicked = {},
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PoemScreenLoadingPreview() {
    PoemThemePreview {
        PoemScreen(
            poetUiModel = PoetUiModel.fixture,
            modifier = Modifier,
            poemUiModel = Loading,
            onRetryClick = {},
            onPoemClick = {},
            onRecitationClicked = {},
        )
    }
}