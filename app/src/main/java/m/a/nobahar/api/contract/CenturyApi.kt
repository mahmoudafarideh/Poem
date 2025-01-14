package m.a.nobahar.api.contract

import m.a.nobahar.api.model.CenturyPoetsDto
import retrofit2.http.GET

interface CenturyApi {
    @GET("ganjoor/centuries")
    suspend fun getCenturies(): List<CenturyPoetsDto>
}