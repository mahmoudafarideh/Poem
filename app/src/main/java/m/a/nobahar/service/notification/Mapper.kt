package m.a.nobahar.service.notification

import com.google.firebase.messaging.RemoteMessage
import kotlinx.serialization.json.Json

fun RemoteMessage.toNotificationData(json: Json) = NotificationData(
    id = data["id"]!!.toLong(),
    title = data["title"]!!,
    description = data["description"]!!,
    shouldAlert = data["shouldAlert"]?.toBoolean() == true,
    image = data["image"],
    action = json.decodeFromString<NotificationAction>(data["action"]!!),
    color = data["color"]!!.toInt()
)

