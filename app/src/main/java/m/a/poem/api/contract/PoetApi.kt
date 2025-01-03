package m.a.poem.api.contract

import m.a.poem.api.model.BookDetailDto
import m.a.poem.api.model.PoetDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PoetApi {

    @GET("ganjoor/poet/{id}?catPoems=false")
    suspend fun getPoetDetails(@Path("id") id: Long): PoetDetailsDto

    @GET("ganjoor/cat/{id}?poems=true&mainSections=false")
    suspend fun getBook(@Path("id") id: Long): BookDetailDto

}