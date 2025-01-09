package m.a.poem.ui

import m.a.poem.domain.model.Poet
import m.a.poem.ui.search.model.SearchBookUiModel
import m.a.poem.ui.search.navigation.SearchRoute
import m.a.poem.ui.shared.model.PoetUiModel

fun Poet.toPoetUiModel() = PoetUiModel(
    name = name,
    nickname = nickName,
    profile = profile,
    id = id
)

fun SearchRoute.Book.toSearchBookUiModel(): SearchBookUiModel =
    SearchBookUiModel(id, name)