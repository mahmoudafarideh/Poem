package m.a.poem.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import m.a.poem.ui.home.model.PoetUiModel
import m.a.poem.ui.theme.PoemThemePreview

@Composable
fun PoetLoadingBox(
    poetUiModel: PoetUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Blue)
                .aspectRatio(.8f)
        )
        Text(
            text = poetUiModel.name,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 12.dp)
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