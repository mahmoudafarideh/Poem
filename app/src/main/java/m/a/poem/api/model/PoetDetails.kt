package m.a.poem.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import m.a.poem.domain.model.PoetDetails

@Serializable
data class PoetDetailsDto(
    val poet: PoetInfoDto,
    @SerialName("cat")
    val poetBooksDto: PoetBooksDto
)

internal fun PoetDetailsDto.toPoetDetails() = PoetDetails(
    poet = poet.toPoet(),
    bio = poet.toPoetBio(),
    books = poetBooksDto.children.map {
        it.toPoetBook()
    }
)
