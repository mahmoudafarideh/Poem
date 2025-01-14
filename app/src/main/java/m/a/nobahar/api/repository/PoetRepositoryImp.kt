package m.a.nobahar.api.repository

import m.a.nobahar.api.contract.PoetApi
import m.a.nobahar.api.model.toPoetDetails
import m.a.nobahar.domain.model.PoetDetails
import m.a.nobahar.domain.repository.PoetRepository
import javax.inject.Inject

class PoetRepositoryImp @Inject constructor(
    private val poetApi: PoetApi
) : PoetRepository {
    override suspend fun getPoet(id: Long): PoetDetails {
        return poetApi.getPoetDetails(id).toPoetDetails()
    }
}