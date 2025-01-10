package m.a.poem.ui.info.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.poem.ui.home.component.AppInfoBar
import m.a.poem.ui.noRippleClickable
import m.a.poem.ui.shared.ui.SabaPreview
import m.a.poem.ui.theme.PoemThemePreview

@Composable
fun InfoDialog(
    modifier: Modifier = Modifier
) {
    val navigation = LocalNavController.comPilotNavController
    Box(
        modifier = modifier
            .fillMaxSize()
            .noRippleClickable {
                navigation.safePopBackStack()
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable {}
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppInfoBar()
            Spacer(modifier = Modifier.height(36.dp))
            Row {
                Icon(
                    imageVector = Icons.Default.Close,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                    modifier = modifier
                        .clip(CircleShape)
                        .clickable {
                        }
                        .padding(12.dp)
                )
            }
        }
    }
}


@SabaPreview
@Composable
private fun InfoDialogPreview() {
    PoemThemePreview {
        InfoDialog()
    }
}