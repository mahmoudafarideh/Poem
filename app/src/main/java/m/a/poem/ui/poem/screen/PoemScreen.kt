package m.a.poem.ui.poem.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import kotlinx.collections.immutable.persistentListOf
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.poem.domain.model.Failed
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.Loaded
import m.a.poem.domain.model.Loading
import m.a.poem.domain.model.NotLoaded
import m.a.poem.ui.book.model.SubPoem
import m.a.poem.ui.poem.model.PoemUiModel
import m.a.poem.ui.poem.model.PoemVerseUiModel
import m.a.poem.ui.shared.components.FetchingDataFailed
import m.a.poem.ui.shared.components.PoetAppBar
import m.a.poem.ui.shared.model.PoetUiModel
import m.a.poem.ui.theme.PoemThemePreview

@Composable
fun PoemScreen(
    poetUiModel: PoetUiModel,
    poemUiModel: LoadableData<PoemUiModel>,
    onRetryClick: () -> Unit,
    onPoemClick: (Long) -> Unit,
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
                    LazyColumn(
                        modifier = Modifier,
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        items(
                            items = poemUiModel.data.verses,
                            key = { it.id }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp)
                                    .padding(
                                        top = when (it.position) {
                                            PoemVerseUiModel.VersePosition.Start -> 12.dp
                                            PoemVerseUiModel.VersePosition.End -> 12.dp
                                        },
                                        bottom = when (it.position) {
                                            PoemVerseUiModel.VersePosition.Start -> 0.dp
                                            PoemVerseUiModel.VersePosition.End -> 12.dp
                                        }
                                    )
                            ) {
                                Text(
                                    text = it.text,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .align(
                                            when (it.position) {
                                                PoemVerseUiModel.VersePosition.Start -> Alignment.CenterStart
                                                PoemVerseUiModel.VersePosition.End -> Alignment.CenterEnd
                                            }
                                        ),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    minLines = 1
                                )
                            }
                            when (it.position) {
                                PoemVerseUiModel.VersePosition.Start -> {}
                                PoemVerseUiModel.VersePosition.End -> {
                                    HorizontalDivider(
                                        modifier = Modifier.padding(vertical = 12.dp)
                                    )
                                }
                            }
                        }
                        item {
                            if (poemUiModel.data.next != null || poemUiModel.data.previous != null) {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Box(modifier = Modifier.weight(1f)) {
                                        poemUiModel.data.previous?.let {
                                            AnotherPoemCard(
                                                it, onPoemClick, Modifier.padding(12.dp)
                                            )
                                        }
                                    }
                                    Box(modifier = Modifier.weight(1f)) {
                                        poemUiModel.data.next?.let {
                                            AnotherPoemCard(
                                                it, onPoemClick, Modifier.padding(12.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Loading -> {
                    Column(modifier = Modifier
                        .padding(12.dp)
                        .shimmer()) {
                        repeat(12) {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)) {
                                if (it % 2 == 1) {
                                    Spacer(modifier = Modifier.weight(.3f))
                                }
                                Spacer(
                                    modifier = Modifier
                                        .weight(0.7f)
                                        .height(18.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray)
                                )
                                if (it % 2 != 1) {
                                    Spacer(modifier = Modifier.weight(.3f))
                                }
                            }

                            if (it % 2 == 1) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 12.dp)
                                )
                            }
                        }
                    }
                }

                NotLoaded -> {}
            }
        }
    }
}

@Composable
private fun AnotherPoemCard(
    poem: SubPoem,
    onPoemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondaryContainer),
        onClick = {
            onPoemClick(poem.id)
        }
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = poem.label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = poem.excerpt,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 1
            )
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
                    )
                )
            ),
            onRetryClick = {},
            onPoemClick = {}
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
            onPoemClick = {}
        )
    }
}