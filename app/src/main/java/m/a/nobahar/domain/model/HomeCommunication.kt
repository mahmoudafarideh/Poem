package m.a.nobahar.domain.model

sealed class HomeCommunication {
    data object AppUpdate : HomeCommunication()
    data class HomeBanner(
        val bannerUrl: String,
        val actionUrl: String,
        val aspect: Float
    ) : HomeCommunication()
}