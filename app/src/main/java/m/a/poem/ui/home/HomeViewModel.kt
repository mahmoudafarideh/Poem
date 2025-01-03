package m.a.poem.ui.home

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import m.a.poem.domain.model.CenturyPoets
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.Loaded
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
                delay(2_000)
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
        profile = profile,
        nickname = nickName
    )

    fun centuryClicked(century: String) {
        state.value.data?.let {
            val centuries = it.labels.map { uiModel ->
                uiModel.copy(isSelected = uiModel.label == century)
            }.toImmutableList()
            updateState {
                Loaded(
                    it.copy(
                        labels = centuries,
                        poets = centuryPoets.firstOrNull { it.name == century }?.poets.orEmpty()
                            .map {
                                it.toPoetUiModel()
                            }.toImmutableList()
                    )
                )
            }
        }
    }

    fun retryClicked() {
        getCenturyPoets()
    }
}