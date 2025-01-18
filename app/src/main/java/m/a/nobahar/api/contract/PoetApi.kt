package m.a.nobahar.api.contract

import m.a.nobahar.api.model.PoetDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PoetApi {

    @GET("v1/poet/{id}")
    suspend fun getPoetDetails(@Path("id") id: Long): PoetDetailsDto

}