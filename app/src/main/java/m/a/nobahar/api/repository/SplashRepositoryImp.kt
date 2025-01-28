package m.a.nobahar.api.repository

import android.content.Context
import androidx.core.content.pm.PackageInfoCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import m.a.nobahar.api.contract.SplashApi
import m.a.nobahar.config.PrefKeys
import m.a.nobahar.domain.model.HomeCommunication
import m.a.nobahar.domain.repository.HomeCommunicationRepository
import m.a.nobahar.domain.repository.SplashRepository
import m.a.nobahar.domain.storage.LocalStorage
import m.a.nobahar.domain.storage.optional
import javax.inject.Inject

class SplashRepositoryImp @Inject constructor(
    private val splashApi: SplashApi,
    private val localStorage: LocalStorage,
    private val homeCommunicationRepository: HomeCommunicationRepository,
    @ApplicationContext private val context: Context,
) : SplashRepository {

    override suspend fun getSplash() {
        val firebaseToken = localStorage.optional<String>(PrefKeys.FirebaseToken)
        val deviceId = localStorage.optional<Long>(PrefKeys.DeviceId)
        val splashData = splashApi.getSplash(
            firebaseToken.getValue(),
            getAppVersion(),
            deviceId.getValue()
        )
        splashData.deviceId?.let {
            deviceId.updateValue(it)
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