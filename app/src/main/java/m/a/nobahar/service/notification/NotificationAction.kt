package m.a.nobahar.service.notification

import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import m.a.nobahar.domain.model.PoemVerse
import m.a.nobahar.ui.artwork.navigation.ArtworkRoute
import m.a.nobahar.ui.artwork.navigation.routes.navigator
import m.a.nobahar.ui.book.navigation.BookRoute
import m.a.nobahar.ui.book.navigation.routes.navigator
import m.a.nobahar.ui.main.MainActivity
import m.a.nobahar.ui.omen.navigation.OmenRoute
import m.a.nobahar.ui.omen.navigation.routes.navigator
import m.a.nobahar.ui.poem.navigation.PoemRoute
import m.a.nobahar.ui.poem.navigation.routes.navigator
import m.a.nobahar.ui.poet.navigation.PoetRoute
import m.a.nobahar.ui.poet.navigation.routes.navigator

@Serializable
@Polymorphic
sealed class NotificationAction {

    abstract fun createIntent(context: Context): Intent

    @Serializable
    @SerialName("OpenApp")
    data object OpenApp : NotificationAction() {
        override fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    @Serializable
    @SerialName("ExternalApp")
    data class ExternalApp(
        val appPackage: String,
        val appUrl: String,
        val webUrl: String
    ) : NotificationAction() {
        override fun createIntent(context: Context): Intent {
            val packageManager = context.packageManager
            val appIntent = packageManager.getLaunchIntentForPackage(appPackage)

            return appIntent?.also { it.setPackage(appPackage) }
                ?: Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
        }
    }

    @Serializable
    @SerialName("WebUrl")
    data class WebUrl(val url: String) : NotificationAction() {
        override fun createIntent(context: Context): Intent {
            return Intent(Intent.ACTION_VIEW, Uri.parse(url))
        }
    }

    @Serializable
    @SerialName("Omen")
    data object Omen : NotificationAction() {
        val navigator = OmenRoute.navigator
        override fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
                extras?.putString(MainActivity.KEY_DESTINATION, navigator.navigator())
                extras?.putString(MainActivity.KEY_DESTINATION_ROUTE, navigator.route())
            }
        }
    }

    @Serializable
    @SerialName("Artwork")
    data class Artwork(
        val first: String,
        val second: String,
        val poetName: String,
        val poemBook: String
    ) : NotificationAction() {

        val navigator = ArtworkRoute(
            PoemVerse(first, 0),
            PoemVerse(second, 0),
            poetName,
            poemBook,
        ).navigator

        override fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
                extras?.putString(MainActivity.KEY_DESTINATION, navigator.navigator())
                extras?.putString(MainActivity.KEY_DESTINATION_ROUTE, navigator.route())
            }
        }
    }

    @Serializable
    @SerialName("Poet")
    data class Poet(
        val name: String,
        val id: Long,
        val nickname: String,
        val avatar: String
    ) : NotificationAction() {
        val navigator = PoetRoute(
            m.a.nobahar.domain.model.Poet(id, name, nickname, avatar)
        ).navigator

        override fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
                extras?.putString(MainActivity.KEY_DESTINATION, Omen.navigator.navigator())
                extras?.putString(MainActivity.KEY_DESTINATION_ROUTE, Omen.navigator.route())
            }
        }
    }

    @Serializable
    @SerialName("PoetBook")
    data class PoetBook(
        val name: String,
        val id: Long,
        val nickname: String,
        val avatar: String,
        val bookId: Long,
        val bookName: String
    ) : NotificationAction() {
        val navigator = BookRoute(
            m.a.nobahar.domain.model.Poet(id, name, nickname, avatar),
            bookId,
            bookName
        ).navigator

        override fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
                extras?.putString(MainActivity.KEY_DESTINATION, Omen.navigator.navigator())
                extras?.putString(MainActivity.KEY_DESTINATION_ROUTE, Omen.navigator.route())
            }
        }
    }

    @Serializable
    @SerialName("Poem")
    data class Poem(val id: Long) : NotificationAction() {
        val navigator = PoemRoute(id).navigator
        override fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
                extras?.putString(MainActivity.KEY_DESTINATION, Omen.navigator.navigator())
                extras?.putString(MainActivity.KEY_DESTINATION_ROUTE, Omen.navigator.route())
            }
        }
    }
}