package m.a.poem.api.contract

import m.a.poem.api.model.BookDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {

    @GET("ganjoor/cat/{id}?poems=true&mainSections=false")
    suspend fun getBook(@Path("id") id: Long): BookDetailDto

}