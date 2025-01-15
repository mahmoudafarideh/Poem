package m.a.nobahar.ui.home.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import m.a.nobahar.R
import m.a.nobahar.ui.goToMarket
import m.a.nobahar.ui.home.model.HomeCommunicationUiModel
import m.a.nobahar.ui.shared.components.UrlImage

@Composable
internal fun HomeCommunicationBar(
    model: HomeCommunicationUiModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        when (model) {
            HomeCommunicationUiModel.AppUpdate -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.new_version_label),
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Button(
                        onClick = {
                            context.goToMarket()
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.update_app_button_label),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }

            is HomeCommunicationUiModel.HomeBanner -> {
                UrlImage(
                    url = model.bannerUrl,
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .aspectRatio(model.aspect)
                        .fillMaxWidth()
                        .clickable {
                            runCatching {
                                Intent(Intent.ACTION_VIEW).apply {
                                    this.data = Uri.parse(model.actionUrl)
                                }.let {
                                    context.startActivity(it)
                                }
                            }
                        },
                    placeholder = {
                        Spacer(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .aspectRatio(model.aspect)
                                .background(Color.Gray)
                                .fillMaxWidth()
                                .shimmer()
                        )
                    }
                )
            }
        }
    }
}