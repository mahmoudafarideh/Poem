package m.a.poem.domain.repository

import m.a.poem.domain.model.PoetBookInfo

interface BookRepository {
    suspend fun getBook(id: Long): PoetBookInfo
}