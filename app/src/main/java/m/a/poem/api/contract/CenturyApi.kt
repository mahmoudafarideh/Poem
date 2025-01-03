package m.a.poem.api.contract

import m.a.poem.api.model.CenturyPoetsDto
import retrofit2.http.GET

interface CenturyApi {
    @GET("ganjoor/centuries")
    suspend fun getCenturies(): List<CenturyPoetsDto>
}