package m.a.nobahar.api.repository

import m.a.nobahar.api.contract.PoemApi
import m.a.nobahar.api.model.toPoemInfo
import m.a.nobahar.domain.model.PoemInfo
import m.a.nobahar.domain.repository.PoemRepository
import javax.inject.Inject

class PoemRepositoryImp @Inject constructor(
    private val poemApi: PoemApi
) : PoemRepository {
    override suspend fun getPoem(id: Long): PoemInfo {
        return poemApi.getPoem(id).toPoemInfo()
    }
}