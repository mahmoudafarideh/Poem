package m.a.poem.domain.repository

import m.a.poem.domain.model.PoemSearchFilter
import m.a.poem.domain.model.PoemSearchResult

interface SearchRepository {
    suspend fun searchPoem(filter: PoemSearchFilter): List<PoemSearchResult>
}