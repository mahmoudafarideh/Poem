package m.a.poem.api.model

import kotlinx.serialization.Serializable

@Serializable
data class SplashDto(
    val hasNewVersion: Boolean,
    val homeBanner: HomeBannerDto?
) {
    @Serializable
    data class HomeBannerDto(
        val bannerUrl: String,
        val actionUrl: String,
        val aspect: Float
    )
}
