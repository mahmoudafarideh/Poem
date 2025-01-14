package m.a.nobahar.api.repository

import m.a.nobahar.api.contract.CenturyApi
import m.a.nobahar.api.model.toCenturyPoets
import m.a.nobahar.domain.model.CenturyPoets
import m.a.nobahar.domain.repository.CenturyPoetsRepository
import javax.inject.Inject

class CenturyPoetsRepositoryImp @Inject constructor(
    private val centuryApi: CenturyApi
) : CenturyPoetsRepository {
    override suspend fun getCenturies(): List<CenturyPoets> {
        return centuryApi.getCenturies().map { it.toCenturyPoets() }
    }
}