package m.a.poem.api.contract

import m.a.poem.api.model.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("ganjoor/poems/search")
    suspend fun searchPoem(
        @Query("PageNumber") page: Int,
        @Query("PageSize") limit: Int,
        @Query("term") term: String,
        @Query("poetId") poetId: Long?,
        @Query("catId") bookId: Long?,
    ): List<SearchResultDto>

}