package m.a.poem.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import m.a.poem.domain.model.Poet

@Serializable
data class PoetDto(
    val id: Long,
    val name: String,
    val nickname: String,
    @SerialName("imageurl")
    val imageUrl: String
)

internal fun PoetDto.toPoet() = Poet(
    id = id,
    name = name,
    nickName = nickname,
    profile = imageUrl
)