package m.a.poem.api.repository

import m.a.poem.api.contract.SplashApi
import m.a.poem.api.storage.optional
import m.a.poem.domain.model.HomeCommunication
import m.a.poem.domain.repository.HomeCommunicationRepository
import m.a.poem.domain.repository.SplashRepository
import m.a.poem.domain.storage.LocalStorage
import javax.inject.Inject

class SplashRepositoryImp @Inject constructor(
    private val splashApi: SplashApi,
    localStorage: LocalStorage,
    private val homeCommunicationRepository: HomeCommunicationRepository
) : SplashRepository {
    private val firebaseToken: String? by localStorage.optional("FirebaseToken")
    override suspend fun getSplash() {
        val splashData = splashApi.getSplash("https://matnnegar.ir/nobahar/splash", firebaseToken)
        when {
            splashData.hasNewVersion -> {
                homeCommunicationRepository.setCommunication(
                    HomeCommunication.AppUpdate
                )
            }
            splashData.homeBanner != null -> {
                homeCommunicationRepository.setCommunication(
                    HomeCommunication.HomeBanner(
                        splashData.homeBanner.bannerUrl,
                        splashData.homeBanner.actionUrl,
                        splashData.homeBanner.aspect
                    )
                )
            }
        }
    }
}