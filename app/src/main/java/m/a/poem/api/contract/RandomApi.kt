package m.a.poem.api.contract

import m.a.poem.api.model.RandomPoemDto
import m.a.poem.api.model.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomApi {

    @GET("ganjoor/poem/random")
    suspend fun getRandomPoem(): RandomPoemDto

}