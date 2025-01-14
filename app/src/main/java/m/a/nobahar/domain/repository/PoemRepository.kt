package m.a.nobahar.domain.repository

import m.a.nobahar.domain.model.PoemInfo

interface PoemRepository {
    suspend fun getPoem(id: Long): PoemInfo
}