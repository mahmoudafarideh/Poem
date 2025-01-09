package m.a.poem.domain.repository

import m.a.poem.domain.model.PoemInfo

interface PoemRepository {
    suspend fun getPoem(id: Long): PoemInfo
}