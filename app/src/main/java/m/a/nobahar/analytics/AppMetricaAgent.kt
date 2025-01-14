package m.a.nobahar.analytics

import android.app.Application
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig

private const val AppMetricaKey = "8e9efd72-73c7-45e9-8e7c-d2260a76f66b"

object AppMetricaAgent {
    fun startAppMetrica(application: Application) {
        val config = AppMetricaConfig
            .newConfigBuilder(AppMetricaKey)
            .withAnrMonitoring(true)
            .build()
        AppMetrica.activate(application, config)
    }

    fun log(event: AnalyticsEvent) {
        AppMetrica.reportEvent(
            event.key, event.parameters
        )
    }
}