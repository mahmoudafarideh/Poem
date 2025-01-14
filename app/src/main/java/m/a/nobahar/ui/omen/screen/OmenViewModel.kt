package m.a.nobahar.ui.omen.screen

import dagger.hilt.android.lifecycle.HiltViewModel
import m.a.nobahar.domain.model.LoadableData
import m.a.nobahar.domain.model.NotLoaded
import m.a.nobahar.domain.repository.RandomRepository
import m.a.nobahar.ui.omen.model.OmenUiModel
import m.a.nobahar.ui.shared.BaseViewModel
import m.a.nobahar.ui.toPoetUiModel
import javax.inject.Inject

@HiltViewModel
class OmenViewModel @Inject constructor(
    private val randomRepository: RandomRepository
) : BaseViewModel<LoadableData<OmenUiModel>>(NotLoaded) {

    private fun getOmenPoem() {
        executeLoadable(
            currentValue = state.value,
            action = {
                val poem = randomRepository.getOmenPoem()
                OmenUiModel(
                    poetUiModel = poem.poet.toPoetUiModel(),
                    poemId = poem.id
                )
            },
            data = {
                updateState { it }
            }
        )
    }

    fun retryClicked() {
        getOmenPoem()
    }

    fun errorDismissed() {
        updateState {
            NotLoaded
        }
    }

    fun omenClicked() {
        getOmenPoem()
    }

    fun navigatedToPoemScreen() {
        updateState { NotLoaded }
    }
}