package m.a.nobahar.ui.artwork.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import m.a.nobahar.ui.artwork.model.ArtFontUiModel
import m.a.nobahar.ui.artwork.model.ArtScreenUiModel
import m.a.nobahar.ui.artwork.model.ArtTabUiModel
import kotlin.math.roundToInt

@Composable
fun ArtworkTools(
    state: ArtScreenUiModel,
    onTabClick: (ArtTabUiModel.Tab) -> Unit,
    onFontClick: (ArtFontUiModel.Font) -> Unit,
    onFontSizeChange: (Int) -> Unit,
    onColorClick: (Color) -> Unit,
    onBackgroundClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            state.tabs.forEach {
                FilterChip(
                    selected = it.selected,
                    onClick = {
                        onTabClick(it.tab)
                    },
                    label = {
                        Text(
                            text = stringResource(it.tab.title),
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    shape = CircleShape,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.size(24.dp))

        when (state.selectedTab.tab) {
            ArtTabUiModel.Tab.Font -> {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .fillMaxWidth()
                ) {
                    state.fonts.forEach {
                        SelectiveArtworkItem(
                            selected = it.selected,
                            onClick = {
                                onFontClick(it.font)
                            },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            FontBox(
                                fontFamily = it.font.fontFamily,
                                fontName = stringResource(it.font.label)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.size(24.dp))

                Slider(
                    value = state.selectedFontSize.size.progress.toFloat(),
                    onValueChange = {
                        onFontSizeChange(it.roundToInt())
                    },
                    steps = 7,
                    valueRange = 1f..9f,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            ArtTabUiModel.Tab.Background -> {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .fillMaxWidth(),
                ) {
                    state.backgrounds.forEach {
                        SelectiveArtworkItem(
                            selected = it.selected,
                            onClick = {
                                onBackgroundClick(it.image)
                            },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            BackgroundBox(
                                image = it.image
                            )
                        }
                    }
                }
            }

            ArtTabUiModel.Tab.Color -> {

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .fillMaxWidth()
                ) {
                    state.colors.forEach {
                        SelectiveArtworkItem(
                            selected = it.selected,
                            onClick = {
                                onColorClick(it.color)
                            },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            ColorBox(color = it.color)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.size(56.dp))

    }
}