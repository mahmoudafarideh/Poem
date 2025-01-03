package m.a.poem.ui.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest

@Composable
fun UrlImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = Color.Unspecified,
    placeholder: (@Composable () -> Unit)? = null,
) {
    Box {
        var isImageLoaded by remember { mutableStateOf(false) }
        val ctx = LocalContext.current
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(ctx)
                .data(url)
                .build(),
            onSuccess = {
                isImageLoaded = true
            }
        )
        if (!isImageLoaded) {
            placeholder?.invoke()
        }
        Icon(
            tint = tint,
            painter = painter,
            modifier = modifier,
            contentDescription = contentDescription
        )
    }
}