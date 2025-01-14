package m.a.nobahar.domain.model

data class PoetDetails(
    val poet: Poet,
    val books: List<PoetBook>,
    val bio: PoetBio
)
