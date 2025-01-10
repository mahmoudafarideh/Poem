package m.a.poem.api.contract

import m.a.poem.api.model.SplashDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface SplashApi {
    @GET
    suspend fun getSplash(
        @Url splashUrl: String,
        @Header("firebase") firebaseToken: String?,
    ): SplashDto
}