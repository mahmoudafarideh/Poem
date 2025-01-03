package m.a.poem.ui.home.model

data class PoetUiModel(
    val name: String,
    val profile: String,
    val id: Long,
    val nickname: String = name,
)