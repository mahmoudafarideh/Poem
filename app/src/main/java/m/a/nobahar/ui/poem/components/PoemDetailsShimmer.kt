package m.a.nobahar.ui.poem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import m.a.nobahar.ui.shared.ui.LocalWindowSize
import m.a.nobahar.ui.shared.ui.SabaPreview
import m.a.nobahar.ui.theme.PoemThemePreview
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass


@Composable
internal fun PoemDetailsShimmer(
    modifier: Modifier = Modifier
) {

    val windowSize = LocalWindowSize.current
    Column(
        modifier = modifier
            .padding(12.dp)
            .padding(
                horizontal =
                    when (windowSize.widthSizeClass) {
                        WindowWidthSizeClass.Expanded -> 148.dp
                        else -> 0.dp
                    }
            )
            .shimmer()
    ) {
        repeat(12) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
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

@SabaPreview
@Composable
fun PoemDetailsShimmerPreview() {
    PoemThemePreview {
        PoemDetailsShimmer()
    }
}