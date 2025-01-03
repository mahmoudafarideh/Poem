package m.a.poem.api.model

import kotlinx.serialization.Serializable
import m.a.poem.di.WEB_URL
import m.a.poem.domain.model.Poet
import m.a.poem.domain.model.PoetBio

@Serializable
data class PoetInfoDto(
    val id: Long,
    val name: String,
    val nickname: String,
    val imageUrl: String,
    val description: String,
    val birthPlace: String,
)

internal fun PoetInfoDto.toPoet() = Poet(
    id = id,
    name = name,
    nickName = nickname,
    profile = WEB_URL + imageUrl
)

internal fun PoetInfoDto.toPoetBio() = PoetBio(
    text = description,
    bornCity = birthPlace
)