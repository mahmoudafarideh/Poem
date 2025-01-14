package m.a.nobahar.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SplashDto(
    val hasNewVersion: Boolean,
    val homeBanner: HomeBannerDto?,
    @SerialName("device_id")
    val deviceId: Long?
) {
    @Serializable
    data class HomeBannerDto(
        val bannerUrl: String,
        val actionUrl: String,
        val aspect: Float
    )
}
