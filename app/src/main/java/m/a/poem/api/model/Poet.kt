package m.a.poem.api.model

import kotlinx.serialization.Serializable
import m.a.poem.di.WEB_URL
import m.a.poem.domain.model.Poet

@Serializable
data class PoetDto(
    val id: Long,
    val name: String,
    val nickname: String,
    val imageUrl: String?
)

internal fun PoetDto.toPoet() = Poet(
    id = id,
    name = name,
    nickName = nickname,
    profile = WEB_URL + imageUrl.orEmpty()
)