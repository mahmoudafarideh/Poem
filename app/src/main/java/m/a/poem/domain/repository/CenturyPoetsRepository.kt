package m.a.poem.domain.repository

import m.a.poem.domain.model.CenturyPoets

interface CenturyPoetsRepository {
    suspend fun getCenturies(): List<CenturyPoets>
}