package m.a.poem.api.contract

import m.a.poem.api.model.BookDetailDto
import m.a.poem.api.model.PoemInfoDto
import m.a.poem.api.model.PoetDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PoetApi {

    @GET("ganjoor/poet/{id}?catPoems=false")
    suspend fun getPoetDetails(@Path("id") id: Long): PoetDetailsDto

    @GET("ganjoor/cat/{id}?poems=true&mainSections=false")
    suspend fun getBook(@Path("id") id: Long): BookDetailDto

    @GET("ganjoor/poem/{id}?catInfo=true&catPoems=false&rhymes=true&recitations=true&images=true&songs=true&comments=true&verseDetails=true&navigation=true&relatedpoems=true")
    suspend fun getPoem(@Path("id") id: Long): PoemInfoDto

}