package m.a.poem.api.repository

import m.a.poem.api.contract.PoetApi
import m.a.poem.api.contract.RandomApi
import m.a.poem.api.model.toRandomPoem
import m.a.poem.domain.model.RandomPoem
import m.a.poem.domain.repository.RandomRepository
import javax.inject.Inject

class RandomRepositoryImp @Inject constructor(
    private val randomApi: RandomApi,
    private val poetApi: PoetApi
) : RandomRepository {
    override suspend fun getRandomPoem(): RandomPoem {
        return randomApi.getRandomPoem().let {
            val poem = poetApi.getPoem(it.id)
            it.toRandomPoem(poem.source.poet, poem.source.book)
        }
    }
}