package m.a.poem.ui.book.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import m.a.poem.R

@Composable
internal fun PoemBioLoading(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        repeat(3) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 18.dp)
                    .padding(horizontal = 24.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.poem),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.size(12.dp))
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .size(48.dp, 12.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(start = 56.dp)
            )
        }
    }
}