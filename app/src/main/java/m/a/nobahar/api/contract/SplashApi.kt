package m.a.nobahar.api.contract

import m.a.nobahar.api.model.SplashDto
import retrofit2.http.GET
import retrofit2.http.Header

interface SplashApi {
    @GET("v1/splash")
    suspend fun getSplash(
        @Header("firebaseToken") firebaseToken: String?,
        @Header("appVersion") appVersion: Long,
        @Header("deviceId") deviceId: Long?,
    ): SplashDto
}