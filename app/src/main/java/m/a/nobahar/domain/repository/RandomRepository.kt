package m.a.nobahar.domain.repository

import m.a.nobahar.domain.model.RandomPoem

interface RandomRepository {
    suspend fun getRandomPoem(poetId: Long? = null): RandomPoem
    suspend fun getOmenPoem(): RandomPoem
}