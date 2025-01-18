package m.a.nobahar.api.contract

import m.a.nobahar.api.model.BookDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {

    @GET("v1/book/{id}")
    suspend fun getBook(@Path("id") id: Long): BookDetailDto

}