package m.a.poem.ui.artwork.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import m.a.poem.R
import m.a.poem.ui.artwork.model.ArtSavingState
import m.a.poem.ui.artwork.model.ArtScreenUiModel

@Composable
internal fun ColumnScope.SavingStateMessages(
    state: ArtScreenUiModel,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        AnimatedVisibility(
            visible = state.savingState == ArtSavingState.Saved,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            PhotoSaveInfoCard(
                Color.Green,
                stringResource(R.string.artwork_saved_message),
                Icons.Default.CheckCircle,
                Modifier.padding(16.dp)
            )
        }
        AnimatedVisibility(
            visible = state.savingState == ArtSavingState.Saving,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            PhotoSaveInfoCard(
                MaterialTheme.colorScheme.primary,
                stringResource(R.string.artwork_saving_message),
                Icons.Default.Info,
                Modifier.padding(16.dp)
            )
        }
        AnimatedVisibility(
            visible = state.savingState == ArtSavingState.Failed,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            PhotoSaveInfoCard(
                MaterialTheme.colorScheme.error,
                stringResource(R.string.artwork_saving_failed_message),
                Icons.Default.Error,
                Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun PhotoSaveInfoCard(
    color: Color,
    message: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .height(56.dp)
                    .width(12.dp)
                    .background(color)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = message
            )
        }
    }
}
