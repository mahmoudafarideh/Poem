package m.a.nobahar.api.contract

import m.a.nobahar.api.model.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("v1/poem/search")
    suspend fun searchPoem(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("term") term: String,
        @Query("poetId") poetId: Long?,
        @Query("bookId") bookId: Long?,
    ): List<SearchResultDto>

}