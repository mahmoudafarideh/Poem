package m.a.nobahar.domain.repository

import m.a.nobahar.domain.model.CenturyPoets

interface CenturyPoetsRepository {
    suspend fun getCenturies(): List<CenturyPoets>
}