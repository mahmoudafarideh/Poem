package m.a.poem.domain.repository

import m.a.poem.domain.model.RandomPoem

interface RandomRepository {
    suspend fun getRandomPoem(): RandomPoem
}