package m.a.poem.ui.base.components

import android.content.res.Configuration
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import m.a.poem.R
import m.a.poem.ui.theme.PoemThemePreview

@Composable
fun FetchingDataFailed(
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.errorContainer)
        ) {
            Icon(
                painter = painterResource(android.R.drawable.ic_menu_info_details),
                tint = MaterialTheme.colorScheme.error,
                contentDescription = null,
                modifier = Modifier.size(56.dp)
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
        Text(
            text = stringResource(R.string.error_occured_label),
            modifier = Modifier,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.size(24.dp))
        Button(
            onClick = onRetryClick,
        ) {
            Text(
                text = stringResource(R.string.retry_button_label),
                modifier = Modifier,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FetchingDataFailedPreview() {
    PoemThemePreview {
        FetchingDataFailed(
            onRetryClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}