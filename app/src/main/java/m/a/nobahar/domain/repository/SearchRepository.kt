package m.a.nobahar.domain.repository

import m.a.nobahar.domain.model.PoemSearchFilter
import m.a.nobahar.domain.model.PoemSearchResult

interface SearchRepository {
    suspend fun searchPoem(filter: PoemSearchFilter): List<PoemSearchResult>
}