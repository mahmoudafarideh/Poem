package m.a.poem.api.repository

import m.a.poem.api.contract.PoetApi
import m.a.poem.api.model.toPoemInfo
import m.a.poem.api.model.toPoetBookInfo
import m.a.poem.api.model.toPoetDetails
import m.a.poem.domain.model.PoemInfo
import m.a.poem.domain.model.PoetBookInfo
import m.a.poem.domain.model.PoetDetails
import m.a.poem.domain.repository.PoetRepository
import javax.inject.Inject

class PoetRepositoryImp @Inject constructor(
    private val poetApi: PoetApi
) : PoetRepository {
    override suspend fun getPoet(id: Long): PoetDetails {
        return poetApi.getPoetDetails(id).toPoetDetails()
    }

    override suspend fun getBook(id: Long): PoetBookInfo {
        return poetApi.getBook(id).toPoetBookInfo()
    }

    override suspend fun getPoem(id: Long): PoemInfo {
        return poetApi.getPoem(id).toPoemInfo()
    }
}