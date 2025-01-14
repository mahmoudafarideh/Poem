package m.a.nobahar.domain.repository

import m.a.nobahar.domain.model.PoetDetails

interface PoetRepository {
    suspend fun getPoet(id: Long): PoetDetails
}