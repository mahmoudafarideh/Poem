package m.a.poem.ui.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import m.a.poem.R
import m.a.poem.ui.shared.ui.LocalWindowSize
import m.a.poem.ui.shared.ui.SabaPreview
import m.a.poem.ui.theme.PoemThemePreview


@Composable
internal fun SearchResultShimmerList(
    modifier: Modifier = Modifier,
) {
    val windowSize = LocalWindowSize.current
    val horizontalPadding = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> 48.dp
        else -> 0.dp
    }
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        repeat(10) {
            SearchPoemShimmer(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .padding(vertical = 12.dp)
                    .padding(horizontal = 24.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(start = 56.dp)
            )
        }
    }
}

@Composable
internal fun SearchPoemShimmer(modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.shimmer()
    ) {
        Box(modifier = Modifier.width(48.dp)) {
            Image(
                painter = painterResource(R.drawable.circle),
                modifier = Modifier.aspectRatio(.8f),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(Color.Gray)
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Column {
            Spacer(modifier = Modifier.size(4.dp))
            Spacer(
                modifier = Modifier
                    .width(68.dp)
                    .height(12.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
            )
            Spacer(modifier = Modifier.size(12.dp))
            Spacer(
                modifier = Modifier
                    .width(86.dp)
                    .height(12.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
            )
            Spacer(modifier = Modifier.size(12.dp))
            Spacer(
                modifier = Modifier
                    .width(48.dp)
                    .height(12.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@SabaPreview
@Composable
private fun SearchResultShimmerListPreview() {
    PoemThemePreview {
        SearchResultShimmerList()
    }
}