package m.a.nobahar.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import m.a.nobahar.analytics.AppMetricaAgent

@HiltAndroidApp
class PoemApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppMetricaAgent.startAppMetrica(this)
    }
}