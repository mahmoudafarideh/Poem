package m.a.poem.ui.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import m.a.poem.ui.shared.model.PoetUiModel
import m.a.poem.ui.shared.ui.SabaPreview
import m.a.poem.ui.theme.PoemThemePreview

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun PoetAppBar(
    poetUiModel: PoetUiModel,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                UrlImage(
                    url = poetUiModel.profile,
                    contentDescription = poetUiModel.name,
                    modifier = Modifier.aspectRatio(.8f),
                    placeholder = {
                        PoetProfilePlaceholder(poetUiModel)
                    }
                )
                Spacer(modifier = Modifier.size(12.dp))
                Column {
                    Text(
                        text = poetUiModel.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = poetUiModel.nickname,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onBackClick()
                    }
                    .padding(12.dp)
            )
        },
        modifier = modifier,
        actions = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onSearchClick() }
                    .padding(12.dp)
            )
        }
    )
}

@SabaPreview
@Composable
internal fun PoetAppBarPreview() {
    PoemThemePreview {
        PoetAppBar(
            poetUiModel = PoetUiModel.fixture,
            onBackClick = {},
            onSearchClick = {},
        )
    }
}