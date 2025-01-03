package m.a.poem.ui.home

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import m.a.poem.domain.model.CenturyPoets
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.NotLoaded
import m.a.poem.domain.model.Poet
import m.a.poem.domain.repository.CenturyPoetsRepository
import m.a.poem.ui.base.BaseViewModel
import m.a.poem.ui.home.model.CenturyUiModel
import m.a.poem.ui.home.model.HomeUiModel
import m.a.poem.ui.home.model.PoetUiModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CenturyPoetsRepository
) : BaseViewModel<LoadableData<HomeUiModel>>(NotLoaded) {

    private var centuryPoets: List<CenturyPoets> = emptyList()

    init {
        getCenturyPoets()
    }

    private fun getCenturyPoets() {
        executeLoadable(
            currentValue = state.value,
            action = {
                getPoets()
            },
            data = {
                updateState { it }
            }
        )
    }

    private suspend fun getPoets(): HomeUiModel {
        val centuries = repository.getCenturies()
        val popularPoets = centuries.firstOrNull()?.poets.orEmpty().map {
            it.toPoetUiModel()
        }.toImmutableList()
        val realCenturies = centuries.filter { it.id > 0 }
        centuryPoets = realCenturies
        val labels = realCenturies.mapIndexed { index, centuryPoets ->
            toCenturyUiModel(centuryPoets, index)
        }.toImmutableList()

        val poets = realCenturies.firstOrNull()?.poets.orEmpty().map {
            it.toPoetUiModel()
        }.toImmutableList()

        return HomeUiModel(
            popularPoets = popularPoets,
            labels = labels,
            poets = poets
        )
    }

    private fun toCenturyUiModel(
        centuryPoets: CenturyPoets,
        index: Int
    ) = CenturyUiModel(
        label = centuryPoets.name,
        isSelected = index == 0
    )

    private fun Poet.toPoetUiModel() = PoetUiModel(
        id = id,
        name = name,
        profile = profile
    )
}