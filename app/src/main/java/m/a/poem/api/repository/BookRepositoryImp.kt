package m.a.poem.api.repository

import m.a.poem.api.contract.BookApi
import m.a.poem.api.model.toPoetBookInfo
import m.a.poem.domain.model.PoetBookInfo
import m.a.poem.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImp @Inject constructor(
    private val bookApi: BookApi
) : BookRepository {
    override suspend fun getBook(id: Long): PoetBookInfo {
        return bookApi.getBook(id).toPoetBookInfo()
    }
}