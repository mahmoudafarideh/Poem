package m.a.poem.ui.shared.model

import m.a.poem.domain.model.Poet

data class PoetUiModel(
    val name: String,
    val profile: String,
    val id: Long,
    val nickname: String = name,
) {
    fun toPoet(): Poet = Poet(
        id = id,
        name = name,
        nickName = nickname,
        profile = profile,
    )

    companion object {
        val fixture = PoetUiModel("حافظ", "URL", 2, "حافظ شیرازی")
    }
}

