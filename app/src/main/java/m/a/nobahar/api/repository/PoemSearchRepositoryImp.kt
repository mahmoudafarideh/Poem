package m.a.nobahar.api.repository

import m.a.nobahar.api.contract.SearchApi
import m.a.nobahar.api.model.toSearch
import m.a.nobahar.domain.model.PoemSearchFilter
import m.a.nobahar.domain.model.PoemSearchResult
import m.a.nobahar.domain.repository.SearchRepository
import javax.inject.Inject

class PoemSearchRepositoryImp @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {
    override suspend fun searchPoem(filter: PoemSearchFilter): List<PoemSearchResult> {
        return searchApi.searchPoem(
            page = filter.page,
            limit = filter.limit,
            term = filter.query,
            poetId = filter.poetId,
            bookId = filter.bookId,
        ).map { it.toSearch(filter.query) }
    }

}