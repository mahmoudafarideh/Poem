package m.a.nobahar.api.contract

import m.a.nobahar.api.model.PoemInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import retrofit2.http.Url

interface PoemApi {
    @GET("v1/poem/{id}")
    suspend fun getPoem(@Path("id") id: Long): PoemInfoDto

    @Streaming
    @GET
    suspend fun loadRecitationAudioSync(@Url link: String): ResponseBody
}