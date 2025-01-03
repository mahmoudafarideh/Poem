package m.a.poem.ui.shared.model

data class PoetUiModel(
    val name: String,
    val profile: String,
    val id: Long,
    val nickname: String = name,
) {
    companion object {
        val fixture = PoetUiModel("حافظ", "URL", 2, "حافظ شیرازی")
    }
}