package m.a.poem.api.contract

import m.a.poem.api.model.PoemInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import retrofit2.http.Url

interface PoemApi {
    @GET("ganjoor/poem/{id}?catInfo=true&catPoems=false&rhymes=true&recitations=true&images=true&songs=true&comments=true&verseDetails=true&navigation=true&relatedpoems=true")
    suspend fun getPoem(@Path("id") id: Long): PoemInfoDto

    @Streaming
    @GET
    suspend fun loadRecitationAudioSync(@Url link: String): ResponseBody
}