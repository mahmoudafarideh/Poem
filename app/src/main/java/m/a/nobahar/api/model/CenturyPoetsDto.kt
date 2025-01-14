package m.a.nobahar.api.model

import kotlinx.serialization.Serializable
import m.a.nobahar.domain.model.CenturyPoets

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