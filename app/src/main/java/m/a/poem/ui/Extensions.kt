package m.a.poem.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import m.a.poem.domain.model.Poet
import m.a.poem.ui.search.model.SearchBookUiModel
import m.a.poem.ui.search.navigation.SearchRoute
import m.a.poem.ui.shared.model.PoetUiModel

fun Poet.toPoetUiModel() = PoetUiModel(
    name = name,
    nickname = nickName,
    profile = profile,
    id = id
)

fun SearchRoute.Book.toSearchBookUiModel(): SearchBookUiModel =
    SearchBookUiModel(id, name)

@Composable
fun Modifier.noRippleClickable(
    onClick: () -> Unit
) = then(
    Modifier.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    ) {
        onClick()
    }
)

val LocalSnackBarHostState =
    compositionLocalOf<SnackbarHostState> { error("No SnackbarHostState found!") }

internal fun Context.goToMarket() {
    Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("bazaar://details?id=" + "PACKAGE_NAME")
        setPackage("com.farsitel.bazaar")
    }.let {
        startActivity(it)
    }
}
