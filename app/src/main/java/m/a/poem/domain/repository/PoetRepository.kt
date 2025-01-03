package m.a.poem.domain.repository

import m.a.poem.domain.model.PoetDetails

interface PoetRepository {
    suspend fun getPoet(id: Long): PoetDetails
}