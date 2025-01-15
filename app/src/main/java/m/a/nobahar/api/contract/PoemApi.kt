package m.a.nobahar.api.contract

import m.a.nobahar.api.model.PoemInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import retrofit2.http.Url

interface PoemApi {
    @GET("ganjoor/poem/{id}?catInfo=true&catPoems=false&rhymes=false&recitations=true&images=false&songs=false&comments=false&verseDetails=true&navigation=false&relatedpoems=false")
    suspend fun getPoem(@Path("id") id: Long): PoemInfoDto

    @Streaming
    @GET
    suspend fun loadRecitationAudioSync(@Url link: String): ResponseBody
}