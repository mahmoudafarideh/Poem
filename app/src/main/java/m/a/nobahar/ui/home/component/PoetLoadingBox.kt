package m.a.nobahar.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import m.a.nobahar.R
import m.a.nobahar.ui.shared.model.PoetUiModel
import m.a.nobahar.ui.theme.PoemThemePreview

@Composable
fun PoetLoadingBox(
    poetUiModel: PoetUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.circle),
            modifier = Modifier.aspectRatio(.8f),
            contentDescription = poetUiModel.name,
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(Color.Gray)
        )
        Text(
            text = poetUiModel.name,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 12.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
private fun PoetLoadingBoxPreview() {
    PoemThemePreview {
        PoetLoadingBox(
            poetUiModel = PoetUiModel(
                name = "حافظ",
                profile = "https://api.ganjoor.net/api/ganjoor/poet/image/hafez.gif",
                id = 2
            )
        )
    }
}