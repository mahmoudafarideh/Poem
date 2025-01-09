package m.a.poem.api.repository

import m.a.poem.api.contract.PoemApi
import m.a.poem.api.model.toPoemInfo
import m.a.poem.domain.model.PoemInfo
import m.a.poem.domain.repository.PoemRepository
import javax.inject.Inject

class PoemRepositoryImp @Inject constructor(
    private val poemApi: PoemApi
) : PoemRepository {
    override suspend fun getPoem(id: Long): PoemInfo {
        return poemApi.getPoem(id).toPoemInfo()
    }
}