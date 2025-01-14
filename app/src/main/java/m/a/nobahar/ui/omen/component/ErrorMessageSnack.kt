package m.a.nobahar.ui.omen.component

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import m.a.nobahar.R
import m.a.nobahar.domain.model.Failed
import m.a.nobahar.domain.model.LoadableData
import m.a.nobahar.ui.LocalSnackBarHostState
import m.a.nobahar.ui.omen.model.OmenUiModel

@Composable
internal fun ErrorMessageSnack(
    state: LoadableData<OmenUiModel>,
    onRetryClick: () -> Unit,
    onErrorDismiss: () -> Unit
) {
    val snackbarHostState = LocalSnackBarHostState.current
    val context = LocalContext.current
    LaunchedEffect(state) {
        if (state is Failed) {
            val result = snackbarHostState.showSnackbar(
                message = context.resources.getString(R.string.error_occured_label),
                withDismissAction = false,
                duration = SnackbarDuration.Short
            )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    onRetryClick()
                }

                SnackbarResult.Dismissed -> {
                    onErrorDismiss()
                }
            }
        }
    }
}