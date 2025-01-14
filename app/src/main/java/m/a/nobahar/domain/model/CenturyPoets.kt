package m.a.nobahar.domain.model

data class CenturyPoets(
    val id: Long,
    val name: String,
    val poets: List<Poet>
)