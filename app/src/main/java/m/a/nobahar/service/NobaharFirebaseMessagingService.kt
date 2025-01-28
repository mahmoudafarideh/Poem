package m.a.nobahar.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import m.a.nobahar.R
import m.a.nobahar.config.PrefKeys
import m.a.nobahar.domain.storage.LocalStorage
import m.a.nobahar.domain.storage.optional
import m.a.nobahar.service.notification.toNotificationData
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@AndroidEntryPoint
class NobaharFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var localStorage: LocalStorage

    @Inject
    lateinit var json: Json

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            var firebaseToken= localStorage.optional<String>(PrefKeys.FirebaseToken)
            firebaseToken.updateValue(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notificationData = runCatching {
            message.toNotificationData(json)
        }.getOrNull() ?: return

        val channelId = "messages"

        val notificationBuilder = NotificationCompat
            .Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.logo_naked)
            .setContentTitle(notificationData.title)
            .setContentText(notificationData.description)
            .setAutoCancel(true)
            .setColorized(false)
            .setColor(notificationData.color)
            .setLights(notificationData.color, 1_000, 2_000)


        val pendingIntent = notificationData.action.createIntent(applicationContext).let {
            PendingIntent.getActivity(
                this, 1, it, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
            )
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
            notificationBuilder.setChannelId(channelId)
        }


        pendingIntent?.let {
            notificationBuilder.setContentIntent(it)
        }

        notificationData.image?.let {
            getBitmap(it)
        }?.let {
            notificationBuilder.setLargeIcon(it)
            notificationBuilder.setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(it)
            )
        } ?: kotlin.run {
            notificationBuilder.setStyle(NotificationCompat.BigTextStyle())
        }

        kotlin.runCatching {
            notificationManager.notify(notificationData.id.toInt(), notificationBuilder.build())
        }

    }

    private fun getBitmap(link: String): Bitmap? {
        return try {
            val url = URL(link)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}