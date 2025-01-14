package m.a.nobahar.api.repository

import m.a.nobahar.api.contract.PoemApi
import m.a.nobahar.api.contract.RandomApi
import m.a.nobahar.api.model.toRandomPoem
import m.a.nobahar.domain.model.RandomPoem
import m.a.nobahar.domain.repository.RandomRepository
import javax.inject.Inject

private const val HafizPoetId = 2L

class RandomRepositoryImp @Inject constructor(
    private val randomApi: RandomApi,
    private val poemApi: PoemApi
) : RandomRepository {
    override suspend fun getRandomPoem(): RandomPoem {
        return getRandomPoemInternal()
    }

    private suspend fun getRandomPoemInternal(
        poetId: Long? = null
    ): RandomPoem {
        return randomApi.getRandomPoem(poetId).let {
            val poem = poemApi.getPoem(it.id)
            it.toRandomPoem(poem.source.poet, poem.source.book)
        }
    }

    override suspend fun getOmenPoem(): RandomPoem {
        return getRandomPoemInternal(HafizPoetId)
    }

}