package m.a.nobahar.api.repository

import android.content.Context
import androidx.core.content.pm.PackageInfoCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import m.a.nobahar.api.contract.SplashApi
import m.a.nobahar.api.storage.optional
import m.a.nobahar.config.PrefKeys
import m.a.nobahar.domain.model.HomeCommunication
import m.a.nobahar.domain.repository.HomeCommunicationRepository
import m.a.nobahar.domain.repository.SplashRepository
import m.a.nobahar.domain.storage.LocalStorage
import javax.inject.Inject

class SplashRepositoryImp @Inject constructor(
    private val splashApi: SplashApi,
    localStorage: LocalStorage,
    private val homeCommunicationRepository: HomeCommunicationRepository,
    @ApplicationContext private val context: Context,
) : SplashRepository {
    private val firebaseToken: String? by localStorage.optional(PrefKeys.FirebaseToken)
    private var deviceId: Long? by localStorage.optional(PrefKeys.DeviceId)
    override suspend fun getSplash() {
        val splashData = splashApi.getSplash(
            firebaseToken,
            getAppVersion(),
            deviceId
        )
        splashData.deviceId?.let {
            deviceId = it
        }

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

    private fun getAppVersion(): Long = PackageInfoCompat.getLongVersionCode(
        context.packageManager.getPackageInfo(
            context.packageName,
            0
        )
    )
}