package m.a.poem.domain.model

data class PoetDetails(
    val poet: Poet,
    val books: List<PoetBook>,
    val bio: PoetBio
)
