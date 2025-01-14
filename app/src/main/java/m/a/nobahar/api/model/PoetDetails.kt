package m.a.nobahar.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import m.a.nobahar.domain.model.PoetDetails

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
