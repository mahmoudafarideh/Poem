package m.a.nobahar.api.contract

import m.a.nobahar.api.model.SplashDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface SplashApi {
    @GET
    suspend fun getSplash(
        @Url splashUrl: String,
        @Header("firebaseToken") firebaseToken: String?,
        @Header("appVersion") appVersion: Long,
        @Header("deviceId") deviceId: Long?,
    ): SplashDto
}