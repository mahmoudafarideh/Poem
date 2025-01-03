package m.a.poem.api.repository

import m.a.poem.api.contract.CenturyApi
import m.a.poem.api.model.toCenturyPoets
import m.a.poem.domain.model.CenturyPoets
import m.a.poem.domain.repository.CenturyPoetsRepository
import javax.inject.Inject

class CenturyPoetsRepositoryImp @Inject constructor(
    private val centuryApi: CenturyApi
) : CenturyPoetsRepository {
    override suspend fun getCenturies(): List<CenturyPoets> {
        return centuryApi.getCenturies().map { it.toCenturyPoets() }
    }
}