package m.a.nobahar.api.model

import kotlinx.serialization.Serializable
import m.a.nobahar.di.WEB_URL
import m.a.nobahar.domain.model.Poet

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