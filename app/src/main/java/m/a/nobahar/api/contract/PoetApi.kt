package m.a.nobahar.api.contract

import m.a.nobahar.api.model.PoetDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PoetApi {

    @GET("ganjoor/poet/{id}?catPoems=false")
    suspend fun getPoetDetails(@Path("id") id: Long): PoetDetailsDto

}