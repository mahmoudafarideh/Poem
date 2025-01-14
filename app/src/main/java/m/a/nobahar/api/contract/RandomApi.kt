package m.a.nobahar.api.contract

import m.a.nobahar.api.model.RandomPoemDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomApi {

    @GET("ganjoor/poem/random")
    suspend fun getRandomPoem(
        @Query("poetId") poetId: Long? = null
    ): RandomPoemDto

}