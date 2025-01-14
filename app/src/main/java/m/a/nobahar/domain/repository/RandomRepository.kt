package m.a.nobahar.domain.repository

import m.a.nobahar.domain.model.RandomPoem

interface RandomRepository {
    suspend fun getRandomPoem(): RandomPoem
    suspend fun getOmenPoem(): RandomPoem
}