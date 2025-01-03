package m.a.poem.ui.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import m.a.poem.ui.home.model.PoetUiModel
import m.a.poem.ui.theme.PoemThemePreview

@Composable
fun PoetBox(
    poetUiModel: PoetUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = poetUiModel.profile,
            contentDescription = poetUiModel.name,
            modifier = Modifier.aspectRatio(.8f)
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
private fun PoetBoxPreview() {
    PoemThemePreview {
        PoetBox(
            poetUiModel = PoetUiModel(
                name = "حافظ",
                profile = "https://api.ganjoor.net/api/ganjoor/poet/image/hafez.gif",
                id = 2
            )
        )
    }
}