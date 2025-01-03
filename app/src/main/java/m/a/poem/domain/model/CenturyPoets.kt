package m.a.poem.domain.model

data class CenturyPoets(
    val id: Long,
    val name: String,
    val poets: List<Poet>
)