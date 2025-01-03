package m.a.poem.domain.repository

import m.a.poem.domain.model.PoetBookInfo
import m.a.poem.domain.model.PoetDetails

interface PoetRepository {
    suspend fun getPoet(id: Long): PoetDetails
    suspend fun getBook(id: Long): PoetBookInfo
}