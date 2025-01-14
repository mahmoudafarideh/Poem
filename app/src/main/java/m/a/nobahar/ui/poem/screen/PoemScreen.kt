package m.a.nobahar.ui.poem.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import m.a.compilot.navigation.ComPilotNavController
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.nobahar.domain.model.Failed
import m.a.nobahar.domain.model.LoadableData
import m.a.nobahar.domain.model.Loaded
import m.a.nobahar.domain.model.Loading
import m.a.nobahar.domain.model.NotLoaded
import m.a.nobahar.ui.LocalSnackBarHostState
import m.a.nobahar.ui.artwork.navigation.ArtworkRoute
import m.a.nobahar.ui.artwork.navigation.routes.navigator
import m.a.nobahar.ui.book.model.BookItemUiModel
import m.a.nobahar.ui.book.model.PoemItemUiModel
import m.a.nobahar.ui.poem.components.PoemAppBar
import m.a.nobahar.ui.poem.components.PoemDetailsShimmer
import m.a.nobahar.ui.poem.components.PoemVerses
import m.a.nobahar.ui.poem.components.RecitationsColumn
import m.a.nobahar.ui.poem.model.PoemRecitationUiModel
import m.a.nobahar.ui.poem.model.PoemUiModel
import m.a.nobahar.ui.poem.model.PoemVerseUiModel
import m.a.nobahar.ui.poem.model.toPoemVerse
import m.a.nobahar.ui.shared.components.FetchingDataFailed
import m.a.nobahar.ui.shared.model.PoetUiModel
import m.a.nobahar.ui.shared.ui.SabaPreview
import m.a.nobahar.ui.shared.ui.scrollShadow
import m.a.nobahar.ui.theme.PoemThemePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoemScreen(
    poemUiModel: LoadableData<PoemUiModel>,
    onRetryClick: () -> Unit,
    onVersesCopyClick: () -> Unit,
    onVerseArtworkClick: () -> Unit,
    onPoemClick: (Long) -> Unit,
    onVerseClick: (Int) -> Unit,
    onRecitationClicked: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackBarHostState.current
    val navigation = LocalNavController.comPilotNavController
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    Scaffold(
        topBar = {
            PoemAppBar(
                poemUiModel = poemUiModel,
                onBackClick = { navigation.safePopBackStack() },
                modifier = Modifier.scrollShadow(state),
                onArtworkClick = {
                    artworkIconClicked(
                        poemUiModel,
                        navigation,
                        coroutineScope,
                        snackbarHostState,
                        onVerseArtworkClick
                    )
                },
                onCopyClick = {
                    copyVerses(
                        poemUiModel,
                        clipboardManager,
                        coroutineScope,
                        snackbarHostState,
                        onVersesCopyClick
                    )
                }
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
                        PoemVerses(
                            poemUiModel = poemUiModel,
                            onOtherPoemClick = onPoemClick,
                            onVerseClick = onVerseClick,
                            state = state,
                        )
                    } else {
                        val scaffoldState = rememberBottomSheetScaffoldState()
                        val scope = rememberCoroutineScope()
                        BackHandler(
                            scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded
                        ) {
                            scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                        }
                        BottomSheetScaffold(
                            sheetPeekHeight = 124.dp,
                            sheetContent = {
                                RecitationsColumn(
                                    poemUiModel.data.recitations,
                                    onRecitationClicked
                                )
                            },
                            scaffoldState = scaffoldState
                        ) {
                            PoemVerses(
                                poemUiModel = poemUiModel,
                                onOtherPoemClick = onPoemClick,
                                onVerseClick = onVerseClick,
                                state = state,
                                modifier = Modifier.padding(it)
                            )
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

private fun copyVerses(
    poemUiModel: LoadableData<PoemUiModel>,
    clipboardManager: ClipboardManager,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    onVersesCopyClick: () -> Unit
) {
    poemUiModel.data?.let { poem ->
        if (poem.anyVerseSelected) {
            clipboardManager.setText(
                AnnotatedString(
                    poem.selectedVerses.joinToString("\n\n") {
                        it.first.text + "\n" + it.second.text
                    } + "\n\n" + poem.poetUiModel.nickname + " | " + poem.label
                )
            )
            onVersesCopyClick()
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "کپی شد",
                    withDismissAction = false,
                    duration = SnackbarDuration.Short
                )
            }

        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "حداقل یک بیت را انتخاب کنید!",
                    withDismissAction = false,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}

private fun artworkIconClicked(
    poemUiModel: LoadableData<PoemUiModel>,
    navigation: ComPilotNavController,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    onVerseArtworkClick: () -> Unit
) {
    poemUiModel.data?.let { poem ->
        if (poem.isOneVerseSelected) {
            poem.selectedVerse?.let {
                navigation.navigate(
                    ArtworkRoute(
                        it.first.toPoemVerse(),
                        it.second.toPoemVerse(),
                        poem.poetUiModel.nickname,
                        poem.bookUiModel.label
                    ).navigator
                )
                onVerseArtworkClick()
            }
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "لطفا یک بیت را انتخاب کنید!",
                    withDismissAction = false,
                    duration = SnackbarDuration.Short
                )
            }

        }

    }
}

@SabaPreview
@Composable
private fun PoemScreenPreview() {
    PoemThemePreview {
        PoemScreen(
            modifier = Modifier,
            poemUiModel = Loaded(
                PoemUiModel(
                    verses = persistentListOf(
                        PoemVerseUiModel(
                            first = PoemVerseUiModel.VerseInfo(
                                "ای رستخیز ناگهان وی رحمت بی\u200Cمنتها",
                                1
                            ),
                            second = PoemVerseUiModel.VerseInfo(
                                "ای آتشی افروخته، در بیشه اندیشه\u200Cمنتها",
                                2
                            ),
                            1
                        ),
                    ),
                    next = PoemItemUiModel(
                        label = "غزل شماره ۵",
                        excerpt = "الا ای آهوی وحشی کجایی",
                        id = 12
                    ),
                    previous = PoemItemUiModel(
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
                    ),
                    poetUiModel = PoetUiModel.fixture,
                    bookUiModel = BookItemUiModel.fixture,
                    label = "غزل شماره یک"
                )
            ),
            onRetryClick = {},
            onPoemClick = {},
            onRecitationClicked = {},
            onVerseClick = {},
            onVersesCopyClick = {},
            onVerseArtworkClick = {},
        )
    }
}

@SabaPreview
@Composable
fun PoemScreenLoadingPreview() {
    PoemThemePreview {
        PoemScreen(
            modifier = Modifier,
            poemUiModel = Loading,
            onRetryClick = {},
            onPoemClick = {},
            onRecitationClicked = {},
            onVerseClick = {},
            onVersesCopyClick = {},
            onVerseArtworkClick = {},
        )
    }
}