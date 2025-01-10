package m.a.poem.ui.artwork.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import m.a.poem.R

@Composable
fun ArtworkScreen(
    firstVerse: String,
    secondVerse: String,
    poetName: String,
    bookName: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Box(modifier = Modifier.aspectRatio(1f).padding(16.dp)) {
            Image(
                painter = painterResource(R.drawable.bg_poem_1),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = firstVerse,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.size(24.dp))
                Text(
                    text = secondVerse,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        }
    }
}