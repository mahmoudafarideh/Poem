package m.a.poem.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import com.valentinilk.shimmer.shimmer
import m.a.poem.R
import m.a.poem.ui.base.components.UrlImage
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
        Box {
            UrlImage(
                url = poetUiModel.profile,
                contentDescription = poetUiModel.name,
                modifier = Modifier.aspectRatio(.8f),
                placeholder = {
                    Image(
                        painter = painterResource(R.drawable.circle),
                        modifier = Modifier.aspectRatio(.8f).shimmer(),
                        contentDescription = poetUiModel.name,
                        contentScale = ContentScale.FillBounds,
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )
                }
            )
        }
        Text(
            text = poetUiModel.nickname,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 12.dp),
            textAlign = TextAlign.Center
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