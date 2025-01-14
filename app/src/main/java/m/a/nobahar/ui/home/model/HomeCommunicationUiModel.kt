package m.a.nobahar.ui.home.model

import m.a.nobahar.domain.model.HomeCommunication

sealed class HomeCommunicationUiModel {
    data object AppUpdate : HomeCommunicationUiModel()
    data class HomeBanner(
        val bannerUrl: String,
        val actionUrl: String,
        val aspect: Float
    ) : HomeCommunicationUiModel()
}

internal fun HomeCommunication.toHomeCommunicationUiModel() = when (this) {
    HomeCommunication.AppUpdate -> HomeCommunicationUiModel.AppUpdate
    is HomeCommunication.HomeBanner -> HomeCommunicationUiModel.HomeBanner(
        bannerUrl, actionUrl, aspect
    )
}