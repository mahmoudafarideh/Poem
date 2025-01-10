package m.a.poem.ui.splash.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import m.a.nobahar.R
import m.a.poem.domain.model.Failed
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.Loaded
import m.a.poem.domain.model.Loading
import m.a.poem.domain.model.NotLoaded
import m.a.poem.ui.shared.ui.SabaPreview
import m.a.poem.ui.theme.PoemThemePreview

@Composable
fun SplashScreen(
    state: LoadableData<Unit>,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {},
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NobaharSlogan(
                Modifier.align(Alignment.Center)
            )
            when (state) {
                Failed -> {
                    SplashFailed(
                        onRetryClick,
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp)
                    )
                }

                is Loaded<*> -> {}
                Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp)
                            .size(24.dp),
                    )
                }

                NotLoaded -> {}
            }
        }
    }
}

@Composable
internal fun NobaharSlogan(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.mipmap.logo),
            contentDescription = null,
            modifier = Modifier.size(86.dp)
        )
        Spacer(modifier = Modifier.size(24.dp))
        Column {
            Text(
                text = stringResource(R.string.nobahar),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = stringResource(R.string.nobahar_slogan),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun SplashFailed(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.error_occured_label),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.size(12.dp))
        Button(
            onClick = onClick,
        ) {
            Text(
                text = stringResource(R.string.retry_button_label),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@SabaPreview
@Composable
private fun SplashScreenPreview() {
    PoemThemePreview {
        SplashScreen(
            Loading,
            {},
            Modifier.fillMaxSize(),
        )
    }
}