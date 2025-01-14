package m.a.nobahar.ui.widget.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.action.Action
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import m.a.nobahar.domain.model.Failed
import m.a.nobahar.domain.model.Loaded
import m.a.nobahar.domain.model.Loading
import m.a.nobahar.domain.model.NotLoaded
import m.a.nobahar.ui.theme.WidgetPoemTheme
import m.a.nobahar.ui.widget.model.WidgetUiModel

@Composable
fun WidgetContent(
    state: WidgetUiModel,
    onRetryClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onPoemClick: Action?,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .cornerRadius(24.dp)
            .background(Color.White.copy(alpha = .7f))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        WidgetTitleBar(
            onRefreshClick = onRefreshClick
        )
        Box(
            modifier = GlanceModifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (state.poemVerse) {
                Failed -> {
                    WidgetFetchingDataFailed(
                        onRetryClick = onRetryClick
                    )
                }

                is Loaded<*> -> {
                    state.poemVerse.data?.let {
                        WidgetLoadedContent(
                            firstVerse = it.firstVerse,
                            secondVerse = it.secondVerse,
                            poet = it.poet,
                            book = it.book,
                            modifier = GlanceModifier.fillMaxWidth()
                                .then(
                                    onPoemClick?.let { GlanceModifier.clickable(it) } ?: GlanceModifier
                                )
                        )
                    }

                }

                Loading -> {
                    WidgetLoadingContent()
                }

                NotLoaded -> {}
            }
        }

    }
}


@Suppress("unused")
@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 420, heightDp = 220)
@Composable
fun WidgetContentPreview() {
    WidgetPoemTheme {
        Column(
            modifier = GlanceModifier
                .padding(16.dp)
                .background(Color.Gray)
        ) {
            WidgetContent(
                WidgetUiModel(
                    poemVerse = Loading
                ),
                {},
                {},
                null,
            )
        }
    }
}