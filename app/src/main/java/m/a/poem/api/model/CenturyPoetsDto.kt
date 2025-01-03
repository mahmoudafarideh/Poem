package m.a.poem.api.model

import kotlinx.serialization.Serializable
import m.a.poem.domain.model.CenturyPoets

@Serializable
data class CenturyPoetsDto(
    val id: Long,
    val name: String,
    val poets: List<PoetDto>
)

internal fun CenturyPoetsDto.toCenturyPoets() = CenturyPoets(
    id = id,
    name = name,
    poets = poets.map { it.toPoet() }
)