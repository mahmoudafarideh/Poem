package m.a.poem.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import m.a.poem.analytics.AppMetricaAgent

@HiltAndroidApp
class PoemApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppMetricaAgent.startAppMetrica(this)
    }
}