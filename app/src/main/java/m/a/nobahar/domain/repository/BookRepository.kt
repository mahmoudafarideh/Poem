package m.a.nobahar.domain.repository

import m.a.nobahar.domain.model.PoetBookInfo

interface BookRepository {
    suspend fun getBook(id: Long): PoetBookInfo
}