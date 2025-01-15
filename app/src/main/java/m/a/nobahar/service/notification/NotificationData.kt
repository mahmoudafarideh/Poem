package m.a.nobahar.service.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationData(
    val id: Long,
    val title: String,
    val description: String,
    val shouldAlert: Boolean,
    val image: String?,
    val action: NotificationAction,
    val color: Int
)